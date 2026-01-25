package com.br.xbizitwork.data.remote.faq.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.faq.FaqSectionModel

/**
 * Interface que define o contrato para acesso remoto aos dados de FAQ
 * Camada de abstração entre Repository e API
 */
interface FaqRemoteDataSource {
    /**
     * Obtém as seções de FAQ com suas perguntas
     *
     * @return DefaultResult com lista de seções do FAQ
     */
    suspend fun getFaqSections(): DefaultResult<List<FaqSectionModel>>
}
