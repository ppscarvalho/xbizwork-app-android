package com.br.xbizitwork.domain.usecase.skills

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.skills.ProfessionalSearchResult
import com.br.xbizitwork.domain.repository.SkillsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase para buscar profissionais por habilidade (skill)
 * Segue o mesmo padrão do SaveUserSkillsUseCase
 *
 * Retorna Flow<UiState> para a ViewModel processar estados de Loading, Success, Empty e Error
 */
interface SearchProfessionalBySkillUseCase {
    /**
     * Executa o fluxo de busca de profissionais por habilidade
     *
     * @param parameters Parâmetros contendo o termo de busca
     * @return Flow contendo o estado da operação encapsulado em UiState
     */
    operator fun invoke(parameters: Parameters): Flow<UiState<List<ProfessionalSearchResult>>>

    /**
     * Parâmetros necessários para buscar profissionais
     */
    data class Parameters(
        val skill: String
    )
}

/**
 * Implementação do SearchProfessionalBySkillUseCase
 * Executa a lógica de busca utilizando o SkillsRepository
 * Converte DefaultResult em UiState
 */
class SearchProfessionalBySkillUseCaseImpl @Inject constructor(
    private val repository: SkillsRepository
) : SearchProfessionalBySkillUseCase, FlowUseCase<SearchProfessionalBySkillUseCase.Parameters, List<ProfessionalSearchResult>>() {

    /**
     * Executa a regra de negócio de busca
     * Chama o repository e converte o resultado em UiState
     */
    override suspend fun executeTask(parameters: SearchProfessionalBySkillUseCase.Parameters): UiState<List<ProfessionalSearchResult>> {
        return try {
            when (val response = repository.searchProfessionalsBySkill(parameters.skill)) {
                /**
                 * Busca realizada com sucesso
                 * Retorna lista de profissionais ou estado Empty se vazio
                 */
                is DefaultResult.Success -> {
                    if (response.data.isEmpty()) {
                        UiState.Empty
                    } else {
                        UiState.Success(response.data)
                    }
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
