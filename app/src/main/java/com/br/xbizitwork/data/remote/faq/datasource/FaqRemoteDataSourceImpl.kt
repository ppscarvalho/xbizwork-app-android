package com.br.xbizitwork.data.remote.faq.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.faq.api.FaqApiService
import com.br.xbizitwork.data.remote.faq.mappers.toDomain
import com.br.xbizitwork.domain.model.faq.FaqSection
import javax.inject.Inject

/**
 * Implementação do FaqRemoteDataSource
 * Responsável por chamar a API e converter exceptions em DefaultResult
 * Seguindo o mesmo padrão do SkillsRemoteDataSourceImpl
 */
class FaqRemoteDataSourceImpl @Inject constructor(
    private val apiService: FaqApiService
) : FaqRemoteDataSource {

    override suspend fun getPublicFaqSections(): DefaultResult<List<FaqSection>> {
        return try {
            logInfo("FAQ_DATASOURCE", "📡 Chamando API getPublicFaqSections")

            // Chama a API (retorna ApiResponse wrapper)
            val apiResponse = apiService.getPublicFaqSections()

            // Valida se a resposta foi bem-sucedida
            if (!apiResponse.isSuccessful) {
                logInfo("FAQ_DATASOURCE", "❌ API retornou falha: ${apiResponse.message}")
                return DefaultResult.Error(message = apiResponse.message)
            }

            // Valida se o data não é null
            val responseData = apiResponse.data
            if (responseData == null) {
                logInfo("FAQ_DATASOURCE", "⚠️ API retornou data null")
                return DefaultResult.Success(emptyList())
            }

            logInfo("FAQ_DATASOURCE", "📦 Response recebido: ${responseData.size} seções")
            responseData.forEach { section ->
                logInfo("FAQ_DATASOURCE", "  - Seção: ${section.title} (${section.questions.size} perguntas)")
            }

            // Converte DTOs para modelos de domínio
            val faqSections = responseData.map { it.toDomain() }

            logInfo("FAQ_DATASOURCE", "✅ Seções de FAQ convertidas com sucesso")

            DefaultResult.Success(faqSections)

        } catch (e: Exception) {
            logInfo("FAQ_DATASOURCE", "❌ Erro: ${e.message}")
            // Erro técnico (rede, timeout, etc)
            DefaultResult.Error(message = e.message ?: "Erro desconhecido ao carregar FAQ")
        }
    }
}
