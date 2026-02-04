package com.br.xbizitwork.data.remote.plan.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.plan.dtos.request.SubscribePlanRequest
import com.br.xbizitwork.data.remote.plan.dtos.response.PlanResponse
import com.br.xbizitwork.data.remote.plan.dtos.response.UserPlanResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

/**
 * Implementação do PlanApiService usando Ktor HttpClient
 * Segue o padrão do projeto
 */
class PlanServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): PlanApiService {

    override suspend fun getAllPlans(): ApiResponse<List<PlanResponse>> {
        val response = httpClient.get("plan") {
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    override suspend fun getAllPublicPlans(): ApiResponse<List<PlanResponse>> {
        val response = httpClient.get("plans/public") {
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }

    override suspend fun subscribeToPlan(request: SubscribePlanRequest): ApiResponse<UserPlanResponse> {
        val response = httpClient.post("user-plans") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    override suspend fun getUserCurrentPlan(): ApiResponse<UserPlanResponse?> {
        val response = httpClient.get("user-plans/active") {
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }
}