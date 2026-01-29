package com.br.xbizitwork.data.remote.plan.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.plan.dtos.response.PlanResponse

/**
* API Service para planos de serviço
* Usa ApiResponse<T> genérico para todos os endpoints
*/
interface PlanApiService {
    /**
     * Obtém todos os planos de serviço
     *
     * @return ApiResponse com lista de planos de serviço
     */
    suspend fun getAllPlans(): ApiResponse<List<PlanResponse>>
}