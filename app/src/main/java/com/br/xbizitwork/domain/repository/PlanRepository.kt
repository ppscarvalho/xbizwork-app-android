package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.plan.PlanModel

/**
 * Interface do repositório de Planos de Serviço
 * Define o contrato para operações de Planos de Serviço seguindo Clean Architecture
 */
interface PlanRepository {
    /**
     * Obtém todos os planos de serviço
     *
     * @return DefaultResult com lista de planos de serviço
     */
    suspend fun getAllPlans(): DefaultResult<List<PlanModel>>
}