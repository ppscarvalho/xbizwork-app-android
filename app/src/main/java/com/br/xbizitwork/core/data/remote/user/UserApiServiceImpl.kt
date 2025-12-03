package com.br.xbizitwork.core.data.remote.user

import com.br.xbizitwork.core.data.remote.common.response.ApiResultResponse
import com.br.xbizitwork.core.data.remote.user.request.CreateUserRequest
import com.br.xbizitwork.core.data.remote.user.response.CreateUserResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class UserApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : UserApiService {
    override suspend fun createUser(createUserRequest: CreateUserRequest): ApiResultResponse {
        val response = client.post("users"){
            contentType(ContentType.Application.Json)
            setBody(createUserRequest)
        }
        return response.body()
    }

    override suspend fun getAllUsers(): CreateUserResponse {
        val response = client.get("userss"){
            contentType(ContentType.Application.Json)
        }
        return response.body()
    }
}