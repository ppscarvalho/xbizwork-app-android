package com.br.xbizitwork.domain.usecase.subcategory

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.repository.SubcategoryRepository
import com.br.xbizitwork.domain.result.subcategory.SubcategoryResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase para obter subcategorias por categoria
 */
interface GetSubcategoriesByCategoryUseCase {
    operator fun invoke(categoryId: Int): Flow<UiState<List<SubcategoryResult>>>
}

class GetSubcategoriesByCategoryUseCaseImpl @Inject constructor(
    private val repository: SubcategoryRepository
) : GetSubcategoriesByCategoryUseCase, FlowUseCase<Int, List<SubcategoryResult>>() {
    
    override suspend fun executeTask(parameters: Int): UiState<List<SubcategoryResult>> {
        return try {
            when (val response = repository.getSubcategoriesByCategory(parameters)) {
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
