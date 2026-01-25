package com.br.xbizitwork.data.remote.faq.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.faq.dtos.responses.FaqSectionResponse

/**
 * Interface que define as operações de API relacionadas ao FAQ
 * Endpoint público - não requer autenticação
 */
interface FaqApiService {
    /**
     * Obtém as seções de FAQ com suas perguntas e respostas
     * 
     * @return ApiResponse com lista de seções do FAQ
     */
    suspend fun getFaqSections(): ApiResponse<List<FaqSectionResponse>>
}
