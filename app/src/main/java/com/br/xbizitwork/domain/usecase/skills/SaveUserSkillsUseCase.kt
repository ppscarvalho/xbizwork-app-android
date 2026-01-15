package com.br.xbizitwork.domain.usecase.skills

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.skills.SaveUserSkillsRequestModel
import com.br.xbizitwork.domain.repository.SkillsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase para salvar habilidades do usuário
 * Segue o mesmo padrão do UpdateProfileUseCase
 *
 * Retorna Flow<UiState> para a ViewModel processar estados de Loading, Success e Error
 */
interface SaveUserSkillsUseCase {
    /**
     * Executa o fluxo de salvamento de habilidades
     *
     * @param parameters Parâmetros contendo os IDs das categorias selecionadas
     * @return Flow contendo o estado da operação encapsulado em UiState
     */
    operator fun invoke(parameters: Parameters): Flow<UiState<ApiResultModel>>

    /**
     * Parâmetros necessários para salvar habilidades
     */
    data class Parameters(
        val saveUserSkillsRequestModel: SaveUserSkillsRequestModel
    )
}

/**
 * Implementação do SaveUserSkillsUseCase
 * Executa a lógica de salvamento utilizando o SkillsRepository
 * Converte DefaultResult em UiState
 */
class SaveUserSkillsUseCaseImpl @Inject constructor(
    private val repository: SkillsRepository
) : SaveUserSkillsUseCase, FlowUseCase<SaveUserSkillsUseCase.Parameters, ApiResultModel>() {

    /**
     * Executa a regra de negócio de salvamento
     * Chama o repository e converte o resultado em UiState
     */
    override suspend fun executeTask(parameters: SaveUserSkillsUseCase.Parameters): UiState<ApiResultModel> {
        return try {
            when (val response = repository.saveUserSkills(parameters.saveUserSkillsRequestModel)) {
                /**
                 * Salvamento realizado com sucesso
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
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}

