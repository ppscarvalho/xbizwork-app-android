package com.br.xbizitwork.data.remote.skills.datasource

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.skills.api.SkillsApiService
import com.br.xbizitwork.data.remote.skills.mappers.toRequest
import com.br.xbizitwork.domain.model.skills.SaveUserSkillsRequestModel
import com.br.xbizitwork.domain.source.skills.SkillsRemoteDataSource
import javax.inject.Inject

/**
 * Implementação do SkillsRemoteDataSource
 * Responsável por chamar a API e converter exceptions em DefaultResult
 * Seguindo o mesmo padrão do ProfileRemoteDataSourceImpl
 */
class SkillsRemoteDataSourceImpl @Inject constructor(
    private val apiService: SkillsApiService
) : SkillsRemoteDataSource {

    override suspend fun saveUserSkills(model: SaveUserSkillsRequestModel): DefaultResult<ApiResultModel> {
        return try {
            // Converte domain model para DTO request
            val request = model.toRequest()

            // Chama a API
            val response = apiService.saveUserSkills(request)

            // Retorna sucesso (response já é ApiResultResponse, criando ApiResultModel)
            DefaultResult.Success(
                ApiResultModel(
                    isSuccessful = response.isSuccessful,
                    message = response.message
                )
            )

        } catch (e: Exception) {
            // Erro técnico (rede, timeout, etc)
            DefaultResult.Error(message = e.message ?: "Erro desconhecido ao salvar habilidades")
        }
    }

    override suspend fun getUserSkills(userId: Int): DefaultResult<List<Int>> {
        return try {
            logInfo("SKILLS_DATASOURCE", "📡 Chamando API getUserSkills para userId: $userId")

            // Chama a API
            val response = apiService.getUserSkills(userId)

            logInfo("SKILLS_DATASOURCE", "📦 Response recebido: ${response.size} items")
            response.forEach { item ->
                logInfo("SKILLS_DATASOURCE", "  - categoryId: ${item.categoryId}, description: '${item.categoryDescription}'")
            }

            // Extrai apenas os categoryIds da lista de objetos
            val categoryIds = response.map { it.categoryId }

            logInfo("SKILLS_DATASOURCE", "✅ CategoryIds extraídos: $categoryIds")

            DefaultResult.Success(categoryIds)

        } catch (e: Exception) {
            logInfo("SKILLS_DATASOURCE", "❌ Erro: ${e.message}")
            // Erro técnico (rede, timeout, etc)
            DefaultResult.Error(message = e.message ?: "Erro desconhecido ao carregar habilidades")
        }
    }
}

