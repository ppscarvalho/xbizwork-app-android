package com.br.xbizitwork.data.remote.user.api

import com.br.xbizitwork.data.remote.user.dtos.responses.UserApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

/**
 * Implementação do UserApiService usando Ktor
 */
class UserApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): UserApiService {
    override suspend fun getUserById(userId: Int): UserApiResponse {
        val response = httpClient.get("user/$userId")
        return response.body()
    }
}

