package com.br.xbizitwork.domain.usecase.auth.signup

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.auth.SignUpModel
import com.br.xbizitwork.domain.repository.UserAuthRepository
import com.br.xbizitwork.domain.result.auth.SignUpResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso responsável pelo cadastro de um novo usuário (Sign Up).
 *
 * Este contrato define a execução do fluxo de cadastro, retornando um [Flow]
 * que emite estados de UI através de [UiState], como:
 * - Loading: enquanto a operação está em andamento
 * - Success: quando o cadastro é realizado com sucesso
 * - Error: quando ocorre erro de negócio ou técnico
 */
interface SignUpUseCase {
    /**
     * Executa o fluxo de cadastro do usuário.
     *
     * @param parameters Parâmetros necessários para realizar o Sign Up.
     * @return Flow contendo o estado da operação encapsulado em [UiState].
     */
    operator fun invoke(parameters: Parameters): Flow<UiState<SignUpResult>>

    /**
     * Parâmetros necessários para o cadastro do usuário.
     *
     * @property signUpModel Dados de entrada para criação da conta.
     */
    data class Parameters(val signUpModel: SignUpModel)
}

/**
 * Implementação do [SignUpUseCase].
 *
 * Esta classe executa a lógica de cadastro utilizando o [UserAuthRepository],
 * convertendo o resultado da camada de domínio ([DefaultResult]) em estado de UI ([UiState]).
 *
 * @property authRepository Repositório responsável pelas operações de autenticação.
 */
class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository
) : SignUpUseCase, FlowUseCase<SignUpUseCase.Parameters, SignUpResult>() {

    override suspend fun executeTask(parameters: SignUpUseCase.Parameters): UiState<SignUpResult> {
        return try {
            when (val response = authRepository.signUp(parameters.signUpModel)) {
                is DefaultResult.Success -> {
                    UiState.Success(response.data)
                }

                is DefaultResult.Error -> {
                    UiState.Error(Throwable(response.message))
                }
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
