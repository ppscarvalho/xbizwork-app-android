package com.br.xbizitwork.ui.presentation.features.auth.data.remote.api

import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignInRequest
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignUpRequest
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.response.SignInResponse
import com.br.xbizitwork.core.model.api.ApiResultResponse
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
    override suspend fun signIn(authUserRequest: SignInRequest): SignInResponse {
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