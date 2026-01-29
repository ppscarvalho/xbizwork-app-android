package com.br.xbizitwork.data.remote.plan.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.plan.api.PlanApiService
import com.br.xbizitwork.data.remote.plan.mappers.toDomain
import com.br.xbizitwork.domain.model.plan.PlanModel
import jakarta.inject.Inject

class PlanRemoteDataSourceImpl @Inject constructor(
    private val planApiService: PlanApiService
):PlanRemoteDataSource {
    override suspend fun getAllPlans(): DefaultResult<List<PlanModel>> {
        return try {
            logInfo("PLAN_DATASOURCE", "📡 Chamando API getAllPlans")

            val apiResponse = planApiService.getAllPlans()

            if (!apiResponse.isSuccessful){
                logInfo("PLAN_DATASOURCE", "❌ API retornou falha: ${apiResponse.message}")
                return DefaultResult.Error(message = apiResponse.message)
            }

            // Valida se o data não é null
            val responseData = apiResponse.data
            if (responseData == null) {
                logInfo("PLAN_DATASOURCE", "⚠️ API retornou data null")
                return DefaultResult.Success(emptyList())
            }

            val response = apiResponse.data.map {it.toDomain()}
            logInfo("PLAN_DATASOURCE", "✅ Response recebido: ${response.size} planos")

            DefaultResult.Success(response)

        }catch (e: Exception){
            logInfo("PLAN_DATASOURCE", "❌ Erro: ${e.message}")
            DefaultResult.Error(message = e.message ?: "Erro desconhecido ao carregar planos")
        }
    }
}