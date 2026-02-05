package com.br.xbizitwork.data.remote.plan.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.plan.dtos.request.SubscribePlanRequest
import com.br.xbizitwork.data.remote.plan.dtos.response.PlanResponse
import com.br.xbizitwork.data.remote.plan.dtos.response.UserPlanResponse

/**
* API Service para planos de serviço
* Usa ApiResponse<T> genérico para todos os endpoints
*/
interface PlanApiService {
    /**
     * Obtém todos os planos de serviço (endpoint autenticado - legado)
     *
     * @return ApiResponse com lista de planos de serviço
     */
    suspend fun getAllPlans(): ApiResponse<List<PlanResponse>>

    /**
     * Obtém todos os planos de serviço publicamente (sem autenticação)
     * Endpoint: GET /api/v1/plans/public
     *
     * @return ApiResponse com lista de planos de serviço
     */
    suspend fun getAllPublicPlans(): ApiResponse<List<PlanResponse>>

    /**
     * Assina um plano de serviço (requer autenticação)
     * Endpoint: POST /api/v1/user-plans
     *
     * @param request SubscribePlanRequest com userId e planId
     * @return ApiResponse com dados da assinatura
     */
    suspend fun subscribeToPlan(request: SubscribePlanRequest): ApiResponse<UserPlanResponse>

    /**
     * Obtém o plano atual ativo do usuário autenticado
     * Endpoint: GET /api/v1/user-plans/active
     * Usa o token JWT para identificar o usuário
     *
     * @return ApiResponse com plano ativo ou null
     */
    suspend fun getUserCurrentPlan(): ApiResponse<UserPlanResponse?>
}