package com.br.xbizitwork.domain.usecase.auth.changepassword

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.auth.ChangePasswordModel
import com.br.xbizitwork.domain.repository.UserAuthRepository
import com.br.xbizitwork.domain.result.auth.ChangePasswordResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso responsável pela alteração de senha do usuário.
 *
 * Este contrato define a execução do fluxo de alteração de senha, retornando um [Flow]
 * que emite estados de UI através de [UiState], como:
 * - Loading: enquanto a operação está em andamento
 * - Success: quando a senha é alterada com sucesso
 * - Error: quando ocorre erro de negócio ou técnico
 */
interface ChangePasswordUseCase {
    /**
     * Executa o fluxo de alteração de senha.
     *
     * @param parameters Parâmetros necessários para realizar a alteração de senha.
     * @return Flow contendo o estado da operação encapsulado em [UiState].
     */
    operator fun invoke(parameters: Parameters): Flow<UiState<ChangePasswordResult>>

    /**
     * Parâmetros necessários para a alteração de senha.
     *
     * @property changePasswordModel Dados de entrada para alteração de senha.
     */
    data class Parameters(val changePasswordModel: ChangePasswordModel)
}

/**
 * Implementação do [ChangePasswordUseCase].
 *
 * Esta classe executa a lógica de alteração de senha utilizando o [UserAuthRepository],
 * convertendo o resultado da camada de domínio ([DefaultResult]) em estado de UI ([UiState]).
 *
 * @property authRepository Repositório responsável pelas operações de autenticação.
 */
class ChangePasswordUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository
) : ChangePasswordUseCase, FlowUseCase<ChangePasswordUseCase.Parameters, ChangePasswordResult>() {

    override suspend fun executeTask(parameters: ChangePasswordUseCase.Parameters): UiState<ChangePasswordResult> {
        return try {
            when (val response = authRepository.changePassword(parameters.changePasswordModel)) {
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
