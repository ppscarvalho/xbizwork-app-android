package com.br.xbizitwork.data.remote.skills.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.data.remote.skills.dtos.requests.SaveUserSkillsRequest
import com.br.xbizitwork.data.remote.skills.dtos.responses.UserSkillsResponse

/**
 * Interface que define as operações de API relacionadas às habilidades do usuário
 * Seguindo o mesmo padrão do ProfileApiService
 */
interface SkillsApiService {
    /**
     * Salva as habilidades (categorias) do usuário
     *
     * @param request IDs das categorias selecionadas
     * @return Resposta da API indicando sucesso ou falha
     */
    suspend fun saveUserSkills(request: SaveUserSkillsRequest): ApiResultResponse

    /**
     * Obtém as habilidades salvas do usuário
     *
     * @param userId ID do usuário
     * @return ApiResponse com lista de habilidades (seguindo padrão Specialty/Schedule)
     */
    suspend fun getUserSkills(userId: Int): ApiResponse<List<UserSkillsResponse>>
}

