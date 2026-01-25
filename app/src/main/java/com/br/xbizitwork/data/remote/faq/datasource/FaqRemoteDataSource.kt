package com.br.xbizitwork.data.remote.faq.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.faq.FaqSection

/**
 * Interface for FAQ remote data source
 * Following the same pattern as SkillsRemoteDataSource
 */
interface FaqRemoteDataSource {
    /**
     * Fetches public FAQ sections from the API
     *
     * @return DefaultResult with list of FAQ sections
     */
    suspend fun getPublicFaqSections(): DefaultResult<List<FaqSection>>
}
