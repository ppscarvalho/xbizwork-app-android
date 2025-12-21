package com.br.xbizitwork.domain.usecase.category

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.repository.CategoryRepository
import com.br.xbizitwork.domain.result.category.CategoryResult
import javax.inject.Inject

/**
 * UseCase simples para obter categorias retornando DefaultResult
 * Usado em dropdowns e seleções simples
 */
class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): DefaultResult<List<CategoryResult>> {
        return when (val result = repository.getAllCategory(Unit)) {
            is DomainDefaultResult.Success -> {
                DefaultResult.Success(result.data)
            }
            is DomainDefaultResult.Error -> {
                DefaultResult.Error(message = result.message)
            }
        }
    }
}
