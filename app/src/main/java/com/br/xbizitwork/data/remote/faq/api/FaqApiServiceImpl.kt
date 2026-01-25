package com.br.xbizitwork.data.remote.faq.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.faq.dtos.responses.FaqSectionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

/**
 * Implementação do FaqApiService usando Ktor HttpClient
 * Seguindo o mesmo padrão do SkillsApiServiceImpl
 */
class FaqApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : FaqApiService {

    override suspend fun getPublicFaqSections(): ApiResponse<List<FaqSectionDto>> {
        val response = httpClient.get("faq-sections/public")
        return response.body()
    }
}
