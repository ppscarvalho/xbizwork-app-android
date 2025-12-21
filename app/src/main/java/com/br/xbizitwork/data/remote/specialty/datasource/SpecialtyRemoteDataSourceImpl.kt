package com.br.xbizitwork.data.remote.specialty.datasource

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.specialty.api.SpecialtyApiService
import com.br.xbizitwork.data.remote.specialty.dtos.response.SpecialtyResponse
import javax.inject.Inject

class SpecialtyRemoteDataSourceImpl @Inject constructor(
    private val apiService: SpecialtyApiService
) : SpecialtyRemoteDataSource {
    override suspend fun getSpecialtiesByCategory(categoryId: Int): ApiResponse<List<SpecialtyResponse>> {
        return apiService.getSpecialtiesByCategory(categoryId)
    }
}
