package com.br.xbizitwork.data.remote.skills.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.data.remote.skills.dtos.requests.SaveUserSkillsRequest
import com.br.xbizitwork.data.remote.skills.dtos.responses.UserSkillItemResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

/**
 * Implementação do SkillsApiService usando Ktor HttpClient
 * Seguindo o mesmo padrão do ProfileApiServiceImpl
 */
class SkillsApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : SkillsApiService {

    override suspend fun saveUserSkills(request: SaveUserSkillsRequest): ApiResultResponse {
        val response = httpClient.post("user-skills") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    override suspend fun getUserSkills(userId: Int): ApiResponse<List<UserSkillItemResponse>> {
        val response = httpClient.get("user-skills/user/$userId")
        return response.body()
    }
}

