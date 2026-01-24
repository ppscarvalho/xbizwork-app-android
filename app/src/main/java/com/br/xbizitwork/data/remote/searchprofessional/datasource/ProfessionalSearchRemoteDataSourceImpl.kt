package com.br.xbizitwork.data.remote.searchprofessional.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.searchprofessional.api.ProfessionalSearchApiService
import com.br.xbizitwork.data.remote.searchprofessional.mappers.toDomain
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import javax.inject.Inject

/**
 * Implementation of ProfessionalSearchRemoteDataSource
 * Responsible for calling the API and converting exceptions to DefaultResult
 * Following the same pattern as SkillsRemoteDataSourceImpl
 */
class ProfessionalSearchRemoteDataSourceImpl @Inject constructor(
    private val apiService: ProfessionalSearchApiService
) : ProfessionalSearchRemoteDataSource {

    override suspend fun searchProfessionalsBySkill(skill: String): DefaultResult<List<ProfessionalSearchBySkill>> {
        return try {
            // Call the API
            val response = apiService.searchProfessionalsBySkill(skill)

            // Check if the API call was successful
            if (response.isSuccessful) {
                // Convert DTOs to domain models
                val professionals = response.data?.map { it.toDomain() } ?: emptyList()
                DefaultResult.Success(professionals)
            } else {
                // API returned error
                DefaultResult.Error(message = response.message)
            }

        } catch (e: Exception) {
            // Technical error (network, timeout, etc)
            DefaultResult.Error(message = e.message ?: "Erro desconhecido ao buscar profissionais")
        }
    }
}
