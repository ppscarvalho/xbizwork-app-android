package com.br.xbizitwork.data.remote.auth.api

import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.data.remote.auth.dtos.requests.ChangePasswordRequest
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequest
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequest
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class UserAuthApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): UserAuthApiService {
    override suspend fun signIn(signInRequest: SignInRequest): SignInResponse {
        val response = httpClient.post("auth/signin") {
            contentType(ContentType.Application.Json)
            setBody(signInRequest)
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

    override suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): ApiResultResponse {
        val response = httpClient.patch("auth/change-password") {
            contentType(ContentType.Application.Json)
            setBody(changePasswordRequest)
        }
        return response.body()
    }
}
