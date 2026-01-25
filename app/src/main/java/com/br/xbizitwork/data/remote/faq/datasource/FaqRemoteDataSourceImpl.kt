package com.br.xbizitwork.data.remote.faq.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.faq.api.FaqApiService
import com.br.xbizitwork.data.remote.faq.mappers.toDomain
import com.br.xbizitwork.domain.model.faq.FaqSection
import javax.inject.Inject

/**
 * Implementation of FaqRemoteDataSource
 * Responsible for calling the API and converting exceptions to DefaultResult
 * Following the same pattern as SkillsRemoteDataSourceImpl
 */
class FaqRemoteDataSourceImpl @Inject constructor(
    private val apiService: FaqApiService
) : FaqRemoteDataSource {

    override suspend fun getPublicFaqSections(): DefaultResult<List<FaqSection>> {
        return try {
            logInfo("FAQ_DATASOURCE", "📡 Calling API getPublicFaqSections")

            // Call the API (returns ApiResponse wrapper)
            val apiResponse = apiService.getPublicFaqSections()

            // Validate if the response was successful
            if (!apiResponse.isSuccessful) {
                logInfo("FAQ_DATASOURCE", "❌ API returned failure: ${apiResponse.message}")
                return DefaultResult.Error(message = apiResponse.message)
            }

            // Validate if data is not null
            val responseData = apiResponse.data
            if (responseData == null) {
                logInfo("FAQ_DATASOURCE", "⚠️ API returned null data")
                return DefaultResult.Success(emptyList())
            }

            logInfo("FAQ_DATASOURCE", "📦 Response received: ${responseData.size} sections")
            
            // Map DTOs to domain models
            val faqSections = responseData.map { it.toDomain() }

            logInfo("FAQ_DATASOURCE", "✅ Sections mapped successfully")

            DefaultResult.Success(faqSections)

        } catch (e: Exception) {
            logInfo("FAQ_DATASOURCE", "❌ Error: ${e.message}")
            DefaultResult.Error(message = e.message ?: "Unknown error fetching FAQ")
        }
    }
}
