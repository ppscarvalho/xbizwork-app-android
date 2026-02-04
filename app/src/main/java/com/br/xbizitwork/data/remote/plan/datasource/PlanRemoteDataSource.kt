package com.br.xbizitwork.data.remote.plan.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.plan.PlanModel
import com.br.xbizitwork.domain.model.plan.UserPlanModel

/**
 * Interface que define o contrato para acesso remoto aos dados de Planos de Serviço
 * Camada de abstração entre Repository e API
 */
interface PlanRemoteDataSource {
    /**
     * Obtém todos os planos de serviço (endpoint autenticado - legado)
     */
    suspend fun getAllPlans(): DefaultResult<List<PlanModel>>

    /**
     * Obtém todos os planos de serviço publicamente (sem autenticação)
     */
    suspend fun getAllPublicPlans(): DefaultResult<List<PlanModel>>

    /**
     * Assina um plano de serviço (requer autenticação)
     */
    suspend fun subscribeToPlan(userId: Int, planId: Int): DefaultResult<UserPlanModel>

    /**
     * Obtém o plano atual ativo do usuário autenticado (usa token JWT)
     */
    suspend fun getUserCurrentPlan(): DefaultResult<UserPlanModel?>
}