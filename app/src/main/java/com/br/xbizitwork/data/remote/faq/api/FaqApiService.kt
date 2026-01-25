package com.br.xbizitwork.data.remote.faq.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.faq.dtos.responses.FaqSectionDto

/**
 * Interface that defines FAQ API operations
 * Following the same pattern as SkillsApiService
 */
interface FaqApiService {
    /**
     * Fetches public FAQ sections with their questions
     *
     * @return ApiResponse with list of FAQ sections
     */
    suspend fun getPublicFaqSections(): ApiResponse<List<FaqSectionDto>>
}
