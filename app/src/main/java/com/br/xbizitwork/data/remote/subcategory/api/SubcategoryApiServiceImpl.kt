package com.br.xbizitwork.data.remote.subcategory.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.subcategory.dtos.response.SubcategoryResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class SubcategoryApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : SubcategoryApiService {
    override suspend fun getSubcategoriesByCategory(categoryId: Int): ApiResponse<List<SubcategoryResponse>> {
        val response = httpClient.post("subcategories/list") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("category_id" to categoryId))
        }
        return response.body()
    }
}
