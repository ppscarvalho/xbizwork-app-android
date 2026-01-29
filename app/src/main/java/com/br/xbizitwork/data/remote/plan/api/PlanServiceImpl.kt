package com.br.xbizitwork.data.remote.plan.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.plan.dtos.response.PlanResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import jakarta.inject.Inject

/**
 * Implementação do FaqApiService usando Ktor HttpClient
 * Segue o padrão do SkillsApiServiceImpl
 */
class PlanServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): PlanApiService{
    override suspend fun getAllPlans(): ApiResponse<List<PlanResponse>> {
        val response = httpClient.get("plan"){
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }
}