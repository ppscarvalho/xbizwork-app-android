package com.br.xbizitwork.data.remote.subcategory.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.subcategory.dtos.response.SubcategoryResponseModel

/**
 * Interface para acesso remoto aos dados de subcategorias
 */
interface SubcategoryRemoteDataSource {
    suspend fun getSubcategoriesByCategory(categoryId: Int): DefaultResult<List<SubcategoryResponseModel>>
}
