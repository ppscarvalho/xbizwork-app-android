package com.br.xbizitwork.data.remote.profile.api

import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.data.remote.profile.dtos.requests.UpdateProfileRequest

/**
 * Interface que define as operações de API relacionadas ao perfil do usuário
 * Seguindo o mesmo padrão do UserAuthApiService
 */
interface ProfileApiService {
    /**
     * Atualiza o perfil do usuário
     *
     * @param request Dados do perfil a serem atualizados
     * @return Resposta da API indicando sucesso ou falha
     */
    suspend fun updateProfile(request: UpdateProfileRequest): ApiResultResponse
}

