package com.br.xbizitwork.data.remote.specialty.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.specialty.dtos.response.SpecialtyResponse

/**
 * API Service para especialidades
 * Usa ApiResponse<T> genérico para todos os endpoints
 */
interface SpecialtyApiService {
    /**
     * Retorna lista de especialidades para uma categoria específica
     * GET /api/v1/specialties/categories/{categoryId}
     */
    suspend fun getSpecialtiesByCategory(categoryId: Int): ApiResponse<List<SpecialtyResponse>>
}
