package com.br.xbizitwork.data.remote.user.api

import com.br.xbizitwork.data.remote.user.dtos.responses.UserApiResponse

/**
 * API Service para operações de usuário
 */
interface UserApiService {
    /**
     * Busca dados completos do usuário por ID
     * GET /user/{id}
     * Retorna: { data: {...}, isSuccessful: true, message: "..." }
     */
    suspend fun getUserById(userId: Int): UserApiResponse
}

