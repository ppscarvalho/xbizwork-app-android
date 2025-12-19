package com.br.xbizitwork.data.remote.subcategory.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.subcategory.dtos.response.SubcategoryResponse

/**
 * API Service para subcategorias
 */
interface SubcategoryApiService {
    /**
     * Retorna lista de subcategorias por categoria
     */
    suspend fun getSubcategoriesByCategory(categoryId: Int): ApiResponse<List<SubcategoryResponse>>
}
