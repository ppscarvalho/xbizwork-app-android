package com.br.xbizitwork.domain.usecase.plan

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.plan.UserPlanModel
import com.br.xbizitwork.domain.repository.PlanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interface do caso de uso para assinar um plano de serviço
 * Requer autenticação
 */
interface SubscribeToPlanUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<UserPlanModel>>

    data class Parameters(
        val userId: Int,
        val planId: Int
    )
}

/**
 * Implementação do SubscribeToPlanUseCase
 */
class SubscribeToPlanUseCaseImpl @Inject constructor(
    private val repository: PlanRepository
): SubscribeToPlanUseCase, FlowUseCase<SubscribeToPlanUseCase.Parameters, UserPlanModel>() {

    override suspend fun executeTask(parameters: SubscribeToPlanUseCase.Parameters): UiState<UserPlanModel> {
        return try {
            when (val result = repository.subscribeToPlan(parameters.userId, parameters.planId)) {
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
