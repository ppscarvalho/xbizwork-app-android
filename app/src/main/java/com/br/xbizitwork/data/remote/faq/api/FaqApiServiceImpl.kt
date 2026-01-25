package com.br.xbizitwork.data.remote.faq.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.faq.dtos.responses.FaqSectionResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

/**
 * Implementação do FaqApiService usando Ktor HttpClient
 * Segue o padrão do SkillsApiServiceImpl
 */
class FaqApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : FaqApiService {

    override suspend fun getFaqSections(): ApiResponse<List<FaqSectionResponse>> {
        val response = httpClient.get("faq-sections/public")
        return response.body()
    }
}
