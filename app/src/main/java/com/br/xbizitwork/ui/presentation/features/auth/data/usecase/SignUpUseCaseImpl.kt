package com.br.xbizitwork.ui.presentation.features.auth.data.usecase

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.state.UiState.Error
import com.br.xbizitwork.core.state.UiState.Success
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.repository.UserAuthRepository
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.SignUpUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

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
