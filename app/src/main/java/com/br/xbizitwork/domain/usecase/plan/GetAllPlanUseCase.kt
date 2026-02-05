package com.br.xbizitwork.domain.usecase.plan

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.plan.PlanModel
import com.br.xbizitwork.domain.repository.PlanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Interface do caso de uso para obter todos os planos de serviço
 * Segue o padrão do GetCategoriesUseCase (public data, no parameters needed)
*/
interface GetAllPlanUseCase {
    operator fun invoke(parameters: Unit = Unit): Flow<UiState<List<PlanModel>>>
}

/**
 * Implementação do GetAllPlans
 */
class GetAllPlanUseCaseImpl @Inject constructor(
    private val repository: PlanRepository
): GetAllPlanUseCase, FlowUseCase<Unit, List<PlanModel>>() {
    override suspend fun executeTask(parameters: Unit): UiState<List<PlanModel>> {
        return try {
            when(val result = repository.getAllPlans()){
                is DefaultResult.Success -> {
                    UiState.Success(result.data)
                }
                is DefaultResult.Error -> {
                    UiState.Error(Throwable(result.message))
                }
            }
        }catch (e: Exception){
            UiState.Error(e)
        }
    }
}