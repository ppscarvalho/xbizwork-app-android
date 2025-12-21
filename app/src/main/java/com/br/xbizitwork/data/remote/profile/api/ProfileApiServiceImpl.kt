package com.br.xbizitwork.data.remote.profile.api

import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.data.remote.profile.dtos.requests.UpdateProfileRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ProfileApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
): ProfileApiService {
    override suspend fun updateProfile(request: UpdateProfileRequest): ApiResultResponse {
        val response = httpClient.put("users/${request.id}") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }
}

