package com.br.xbizitwork.data.remote.specialty.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.specialty.dtos.response.SpecialtyResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class SpecialtyApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : SpecialtyApiService {
    override suspend fun getSpecialtiesByCategory(categoryId: Int): ApiResponse<List<SpecialtyResponse>> {
        val response = httpClient.get("specialties/categories/$categoryId")
        return response.body()
    }
}
