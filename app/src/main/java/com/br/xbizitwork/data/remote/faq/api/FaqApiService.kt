package com.br.xbizitwork.data.remote.faq.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.faq.dtos.responses.FaqSectionDto

/**
 * Interface que define as operações de API relacionadas ao FAQ
 * Seguindo o mesmo padrão do SkillsApiService
 */
interface FaqApiService {
    /**
     * Obtém todas as seções públicas de FAQ
     *
     * @return ApiResponse com lista de seções de FAQ
     */
    suspend fun getPublicFaqSections(): ApiResponse<List<FaqSectionDto>>
}
