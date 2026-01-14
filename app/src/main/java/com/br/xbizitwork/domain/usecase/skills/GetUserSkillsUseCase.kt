package com.br.xbizitwork.domain.usecase.skills

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.repository.SkillsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para obter as habilidades (IDs de categorias) salvas do usuário
 * Retorna apenas a lista de IDs
 */
interface GetUserSkillsUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<List<Int>>>

    data class Parameters(
        val userId: Int
    )
}

/**
 * Implementação do GetUserSkillsUseCase
 */
class GetUserSkillsUseCaseImpl @Inject constructor(
    private val repository: SkillsRepository
) : GetUserSkillsUseCase, FlowUseCase<GetUserSkillsUseCase.Parameters, List<Int>>() {

    override suspend fun executeTask(parameters: GetUserSkillsUseCase.Parameters): UiState<List<Int>> {
        return try {
            when (val result = repository.getUserSkills(parameters.userId)) {
                is DefaultResult.Success -> {
                    UiState.Success(result.data)
                }
                is DefaultResult.Error -> {
                    UiState.Error(Throwable(result.message))
                }
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}

