package com.br.xbizitwork.data.remote.plan.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.plan.PlanModel

/**
 * Interface que define o contrato para acesso remoto aos dados de Planos de Serviço
 * Camada de abstração entre Repository e API
 */
interface PlanRemoteDataSource {
    /**
     * Obtém todos os planos de serviço
     *
     * @return DefaultResult com lista de planos de serviço
     */
    suspend fun getAllPlans(): DefaultResult<List<PlanModel>>
}