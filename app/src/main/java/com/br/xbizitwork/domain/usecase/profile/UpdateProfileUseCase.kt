package com.br.xbizitwork.domain.usecase.profile

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.profile.UpdateProfileRequestModel
import com.br.xbizitwork.domain.repository.profile.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * UseCase para atualização de perfil do usuário
 * Segue o mesmo padrão do SignUpUseCase
 *
 * Retorna Flow<UiState> para a ViewModel processar estados de Loading, Success e Error
 */
interface UpdateProfileUseCase {
    /**
     * Executa o fluxo de atualização de perfil
     *
     * @param parameters Parâmetros contendo os dados do perfil
     * @return Flow contendo o estado da operação encapsulado em UiState
     */
    operator fun invoke(parameters: Parameters): Flow<UiState<ApiResultModel>>

    /**
     * Parâmetros necessários para atualização do perfil
     */
    data class Parameters(
        val updateProfileRequestModel: UpdateProfileRequestModel
    )
}

/**
 * Implementação do UpdateProfileUseCase
 * Executa a lógica de atualização utilizando o ProfileRepository
 * Converte DefaultResult em UiState
 */
class UpdateProfileUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : UpdateProfileUseCase, FlowUseCase<UpdateProfileUseCase.Parameters, ApiResultModel>() {

    /**
     * Executa a regra de negócio de atualização
     * Chama o repository e converte o resultado em UiState
     */
    override suspend fun executeTask(parameters: UpdateProfileUseCase.Parameters): UiState<ApiResultModel> {
        return try {
            withContext(coroutineDispatcherProvider.io()) {
                when (val response = repository.updateProfile(parameters.updateProfileRequestModel)) {
                    /**
                     * Atualização realizada com sucesso
                     * Retorna o resultado da API encapsulado em UiState.Success
                     */
                    is DefaultResult.Success -> {
                        UiState.Success(response.data)
                    }
                    /**
                     * Erro de regra de negócio retornado pela API
                     * Converte a mensagem em um Throwable para a UI
                     */
                    is DefaultResult.Error -> {
                        UiState.Error(Throwable(response.message))
                    }
                }
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}

