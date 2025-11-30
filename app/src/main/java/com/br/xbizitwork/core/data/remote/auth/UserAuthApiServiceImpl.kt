package com.br.xbizitwork.core.data.remote.auth


import com.br.xbizitwork.core.data.remote.auth.request.SignInRequest
import com.br.xbizitwork.core.data.remote.auth.request.SignUpRequest
import com.br.xbizitwork.core.data.remote.auth.response.TokenResponse
import com.br.xbizitwork.core.data.remote.common.response.ApiResultResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class UserAuthApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): UserAuthApiService {
    override suspend fun signIn(authUserRequest: SignInRequest): TokenResponse {
        val response = httpClient.post("auth/signin") {
            contentType(ContentType.Application.Json)
            setBody(authUserRequest)
        }
        return response.body()
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): ApiResultResponse {
        val response = httpClient.post("auth/signup") {
            contentType(ContentType.Application.Json)
            setBody(signUpRequest)
        }
        return response.body()
    }
}