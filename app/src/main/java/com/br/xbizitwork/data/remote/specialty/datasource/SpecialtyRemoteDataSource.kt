package com.br.xbizitwork.data.remote.specialty.datasource

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.specialty.dtos.response.SpecialtyResponse

/**
 * DataSource remoto para Especialidades
 */
interface SpecialtyRemoteDataSource {
    suspend fun getSpecialtiesByCategory(categoryId: Int): ApiResponse<List<SpecialtyResponse>>
}
