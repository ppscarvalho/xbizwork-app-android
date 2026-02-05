package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.plan.PlanModel
import com.br.xbizitwork.domain.model.plan.UserPlanModel

/**
 * Interface do repositório de Planos de Serviço
 * Define o contrato para operações de Planos de Serviço seguindo Clean Architecture
 */
interface PlanRepository {
    /**
     * Obtém todos os planos de serviço (endpoint autenticado - legado)
     *
     * @return DefaultResult com lista de planos de serviço
     */
    suspend fun getAllPlans(): DefaultResult<List<PlanModel>>

    /**
     * Obtém todos os planos de serviço publicamente (sem autenticação)
     *
     * @return DefaultResult com lista de planos de serviço
     */
    suspend fun getAllPublicPlans(): DefaultResult<List<PlanModel>>

    /**
     * Assina um plano de serviço (requer autenticação)
     *
     * @param userId ID do usuário
     * @param planId ID do plano
     * @return DefaultResult com dados da assinatura
     */
    suspend fun subscribeToPlan(userId: Int, planId: Int): DefaultResult<UserPlanModel>

    /**
     * Obtém o plano atual ativo do usuário autenticado (via token JWT)
     *
     * @return DefaultResult com plano ativo ou null se não tiver
     */
    suspend fun getUserCurrentPlan(): DefaultResult<UserPlanModel?>
}