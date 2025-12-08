package com.br.xbizitwork.data.remote.category.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.category.dtos.response.CategoryResponse

/**
 * API Service para categorias de profissão
 * Usa ApiResponse<T> genérico para todos os endpoints
 */
interface CategoryApiService {
    /**
     * Retorna lista de todas as categorias disponíveis
     */
    suspend fun getAllCategory(): ApiResponse<List<CategoryResponse>>
}