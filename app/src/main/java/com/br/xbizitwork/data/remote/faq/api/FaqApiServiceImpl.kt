package com.br.xbizitwork.data.remote.faq.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.faq.dtos.responses.FaqSectionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

/**
 * Implementation of FaqApiService using Ktor HttpClient
 * Following the same pattern as SkillsApiServiceImpl
 */
class FaqApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : FaqApiService {

    override suspend fun getPublicFaqSections(): ApiResponse<List<FaqSectionDto>> {
        val response = httpClient.get("faq-sections/public")
        return response.body()
    }
}
