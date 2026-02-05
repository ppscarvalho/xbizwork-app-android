package com.br.xbizitwork.domain.usecase.plan

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.plan.PlanModel
import com.br.xbizitwork.domain.repository.PlanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interface do caso de uso para obter todos os planos de serviço publicamente
 * Endpoint público que não requer autenticação
 */
interface GetAllPublicPlanUseCase {
    operator fun invoke(parameters: Unit = Unit): Flow<UiState<List<PlanModel>>>
}

/**
 * Implementação do GetAllPublicPlanUseCase
 */
class GetAllPublicPlanUseCaseImpl @Inject constructor(
    private val repository: PlanRepository
): GetAllPublicPlanUseCase, FlowUseCase<Unit, List<PlanModel>>() {

    override suspend fun executeTask(parameters: Unit): UiState<List<PlanModel>> {
        return try {
            when (val result = repository.getAllPublicPlans()) {
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
