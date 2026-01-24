package com.br.xbizitwork.domain.usecase.professional

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.repository.ProfessionalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase para buscar profissionais por habilidade (skill)
 * Segue o mesmo padrão do SaveUserSkillsUseCase
 *
 * Retorna Flow<UiState> para a ViewModel processar estados de Loading, Success e Error
 */
interface SearchProfessionalsBySkillUseCase {
    /**
     * Executa a busca de profissionais por habilidade
     *
     * @param parameters Parâmetros contendo o termo de busca da skill
     * @return Flow contendo o estado da operação com lista de profissionais
     */
    operator fun invoke(parameters: Parameters): Flow<UiState<List<ProfessionalSearchBySkill>>>

    /**
     * Parâmetros necessários para buscar profissionais
     */
    data class Parameters(
        val skill: String
    )
}

/**
 * Implementação do SearchProfessionalsBySkillUseCase
 * Executa a lógica de busca utilizando o ProfessionalRepository
 * Converte DefaultResult em UiState
 */
class SearchProfessionalsBySkillUseCaseImpl @Inject constructor(
    private val repository: ProfessionalRepository
) : SearchProfessionalsBySkillUseCase, FlowUseCase<SearchProfessionalsBySkillUseCase.Parameters, List<ProfessionalSearchBySkill>>() {

    /**
     * Executa a regra de negócio de busca
     * Chama o repository e converte o resultado em UiState
     */
    override suspend fun executeTask(parameters: SearchProfessionalsBySkillUseCase.Parameters): UiState<List<ProfessionalSearchBySkill>> {
        return try {
            when (val response = repository.searchProfessionalsBySkill(parameters.skill)) {
                /**
                 * Busca realizada com sucesso
                 * Retorna a lista de profissionais encapsulada em UiState.Success
                 */
                is DefaultResult.Success -> {
                    if(response.data.isEmpty()){
                        UiState.Empty
                    }
                    else{
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