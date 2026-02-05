package com.br.xbizitwork.domain.usecase.plan

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.plan.UserPlanModel
import com.br.xbizitwork.domain.repository.PlanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interface do caso de uso para buscar o plano atual do usuário autenticado
 * Usa o token JWT automaticamente, sem precisar passar userId
 */
interface GetUserCurrentPlanUseCase {
    operator fun invoke(parameters: Unit = Unit): Flow<UiState<UserPlanModel?>>
}

/**
 * Implementação do GetUserCurrentPlanUseCase
 */
class GetUserCurrentPlanUseCaseImpl @Inject constructor(
    private val repository: PlanRepository
): GetUserCurrentPlanUseCase, FlowUseCase<Unit, UserPlanModel?>() {

    override suspend fun executeTask(parameters: Unit): UiState<UserPlanModel?> {
        return try {
            when (val result = repository.getUserCurrentPlan()) {
                is DefaultResult.Success -> {
                    UiState.Success(result.data)
                }
                is DefaultResult.Error -> {
                    UiState.Error(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
