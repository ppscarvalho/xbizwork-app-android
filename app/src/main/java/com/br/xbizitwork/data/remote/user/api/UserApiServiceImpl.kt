package com.br.xbizitwork.data.remote.user.api

import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.user.dtos.responses.UserApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

/**
 * Implementação do UserApiService usando Ktor
 */
class UserApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : UserApiService {

    override suspend fun getUserById(userId: Int): UserApiResponse {
        logInfo("USER_API_SERVICE", "GET user/$userId")

        val response: HttpResponse = httpClient.get("user/$userId")

        logInfo("USER_API_SERVICE", "Status: ${response.status}")
        logInfo("USER_API_SERVICE", "Body: ${response.bodyAsText()}")

        return response.body()
    }
}

