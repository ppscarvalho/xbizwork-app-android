package com.br.xbizitwork.data.remote.skills.api

import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.data.remote.skills.dtos.requests.SaveUserSkillsRequest
import com.br.xbizitwork.data.remote.skills.dtos.responses.SearchProfessionalBySkillResponse
import com.br.xbizitwork.data.remote.skills.dtos.responses.UserSkillsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
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

    override suspend fun getUserSkills(userId: Int): UserSkillsResponse {
        val response = httpClient.get("user-skills/user/$userId")
        return response.body()
    }

    override suspend fun searchProfessionalsBySkill(skill: String): SearchProfessionalBySkillResponse {
        val response = httpClient.get("user-skills/search") {
            parameter("skill", skill)
        }
        return response.body()
    }
}

