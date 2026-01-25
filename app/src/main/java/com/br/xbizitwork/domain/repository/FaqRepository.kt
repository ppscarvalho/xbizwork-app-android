package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.faq.FaqSection

/**
 * Interface do repositório de FAQ
 * Define o contrato para operações de FAQ seguindo Clean Architecture
 */
interface FaqRepository {
    /**
     * Obtém todas as seções públicas de FAQ com suas perguntas
     *
     * @return DefaultResult com lista de seções de FAQ
     */
    suspend fun getPublicFaqSections(): DefaultResult<List<FaqSection>>
}
