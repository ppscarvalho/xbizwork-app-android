package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.result.subcategory.SubcategoryResult

/**
 * Repository para subcategorias
 */
interface SubcategoryRepository {
    suspend fun getSubcategoriesByCategory(categoryId: Int): DomainDefaultResult<List<SubcategoryResult>>
}
