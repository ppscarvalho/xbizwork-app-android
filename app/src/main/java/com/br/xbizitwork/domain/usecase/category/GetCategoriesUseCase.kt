package com.br.xbizitwork.domain.usecase.category

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.repository.CategoryRepository
import com.br.xbizitwork.domain.result.category.CategoryResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para obter categorias
 * Usado em dropdowns e seleções simples
 */
interface GetCategoriesUseCase {
    operator fun invoke(parameters: Unit = Unit): Flow<UiState<List<CategoryResult>>>
}

/**
 * Implementação do GetCategoriesUseCase
 */
class GetCategoriesUseCaseImpl @Inject constructor(
    private val repository: CategoryRepository
) : GetCategoriesUseCase, FlowUseCase<Unit, List<CategoryResult>>() {

    override suspend fun executeTask(parameters: Unit): UiState<List<CategoryResult>> {
        return try {
            when (val result = repository.getAllCategory(Unit)) {
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
