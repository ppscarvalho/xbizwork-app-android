package com.br.xbizitwork.ui.presentation.features.auth.domain.usecase

import com.br.xbizitwork.core.domain.model.ApiResultModel
import com.br.xbizitwork.core.util.common.FlowUseCase
import com.br.xbizitwork.core.util.common.DefaultResult
import com.br.xbizitwork.core.util.common.CoroutineDispatcherProvider
import com.br.xbizitwork.core.util.common.UiState
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignUpRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.repository.UserAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
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
    operator fun invoke(parameters: Parameters): Flow<UiState<ApiResultModel>>
    /**
     * Parâmetros necessários para o cadastro do usuário.
     *
     * @property signUpRequestModel Dados de entrada para criação da conta.
     */
    data class Parameters(
        val signUpRequestModel: SignUpRequestModel
    )
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
    private val authRepository: UserAuthRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : SignUpUseCase,FlowUseCase<SignUpUseCase.Parameters, ApiResultModel>() {
    /**
     * Executa efetivamente a regra de negócio do cadastro.
     *
     * A chamada ao repositório retorna um [DefaultResult]:
     * - Em caso de sucesso, é convertido para [UiState.Success]
     * - Em caso de erro de negócio, é convertido para [UiState.Error]
     *
     * Erros técnicos (ex: exceções de rede) são tratados automaticamente
     * pelo [FlowUseCase].
     *
     * @param parameters Parâmetros do cadastro.
     * @return Estado da UI após a execução do cadastro.
     */
    override suspend fun executeTask(parameters: SignUpUseCase.Parameters): UiState<ApiResultModel> {
        return try {
            withContext(coroutineDispatcherProvider.io()) {
                when (val response = authRepository.signUp(parameters.signUpRequestModel)) {
                    /**
                     * Cadastro realizado com sucesso.
                     * Retorna o resultado da API encapsulado em [UiState.Success].
                     */
                    is DefaultResult.Success -> {
                        UiState.Success(response.data)
                    }
                    /**
                     * Erro de regra de negócio retornado pela API.
                     * Converte a mensagem em um [Throwable] para a UI.
                     */
                    is DefaultResult.Error -> {
                        UiState.Error(Throwable(response.message))
                    }
                }
            }
        }catch (e: Exception){
            UiState.Error(e)
        }
    }
}

