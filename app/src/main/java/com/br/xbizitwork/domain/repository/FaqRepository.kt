package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.faq.FaqSectionModel

/**
 * Interface do repositório de FAQ
 * Define o contrato para operações de FAQ seguindo Clean Architecture
 */
interface FaqRepository {
    /**
     * Obtém as seções de FAQ com suas perguntas e respostas
     *
     * @return DefaultResult com lista de seções do FAQ
     */
    suspend fun getFaqSections(): DefaultResult<List<FaqSectionModel>>
}
