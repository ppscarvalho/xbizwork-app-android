package com.br.xbizitwork.data.remote.faq.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.faq.FaqSection

/**
 * Interface que define o contrato para acesso remoto aos dados de FAQ
 * Camada de abstração entre Repository e API
 */
interface FaqRemoteDataSource {
    /**
     * Obtém todas as seções públicas de FAQ do servidor remoto
     *
     * @return DefaultResult com lista de seções de FAQ
     */
    suspend fun getPublicFaqSections(): DefaultResult<List<FaqSection>>
}
