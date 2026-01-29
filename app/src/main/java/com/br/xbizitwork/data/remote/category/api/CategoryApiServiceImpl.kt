package com.br.xbizitwork.data.remote.category.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.category.dtos.response.CategoryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class CategoryApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : CategoryApiService {
    override suspend fun getAllCategory(): ApiResponse<List<CategoryResponse>> {
        val response = httpClient.get("categories") {
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }
}