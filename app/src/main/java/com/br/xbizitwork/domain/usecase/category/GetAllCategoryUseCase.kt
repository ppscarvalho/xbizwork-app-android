package com.br.xbizitwork.domain.usecase.category

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.repository.CategoryRepository
import com.br.xbizitwork.domain.result.category.CategoryResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase para obter todas as categorias
 * 
 * Padrão:
 * - Interface retorna Flow<UiState<Result>>
 * - Implementação herda de FlowUseCase<Parameters, Result>
 * - executeTask converte DomainDefaultResult → UiState
 */
interface GetAllCategoryUseCase {
    operator fun invoke(parameters: Unit = Unit): Flow<UiState<List<CategoryResult>>>
}

class GetAllCategoryUseCaseImpl @Inject constructor(
    private val repository: CategoryRepository
) : GetAllCategoryUseCase, FlowUseCase<Unit, List<CategoryResult>>() {
    
    override suspend fun executeTask(parameters: Unit): UiState<List<CategoryResult>> {
        return try {
            when (val response = repository.getAllCategory(parameters)) {
                is DomainDefaultResult.Success -> {
                    UiState.Success(response.data)
                }

                is DomainDefaultResult.Error -> {
                    UiState.Error(Throwable(response.message))
                }
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
