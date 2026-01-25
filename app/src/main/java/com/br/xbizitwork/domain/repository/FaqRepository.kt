package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.faq.FaqSection

/**
 * Repository interface for FAQ operations
 * Following Clean Architecture pattern
 */
interface FaqRepository {
    /**
     * Fetches public FAQ sections with their questions
     *
     * @return DefaultResult with list of FAQ sections
     */
    suspend fun getPublicFaqSections(): DefaultResult<List<FaqSection>>
}
