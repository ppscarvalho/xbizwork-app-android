package com.br.xbizitwork.data.remote.searchprofessional.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.searchprofessional.dtos.responses.ProfessionalSearchBySkillDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

/**
 * Implementation of ProfessionalSearchApiService using Ktor HttpClient
 * Following the same pattern as SkillsApiServiceImpl
 */
class ProfessionalSearchApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : ProfessionalSearchApiService {

    override suspend fun searchProfessionalsBySkill(skill: String): ApiResponse<List<ProfessionalSearchBySkillDto>> {
        val response = httpClient.get("user-skills/search") {
            parameter("skill", skill)
        }
        return response.body()
    }
}
