package com.br.xbizitwork.data.remote.plan.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.plan.api.PlanApiService
import com.br.xbizitwork.data.remote.plan.dtos.request.SubscribePlanRequest
import com.br.xbizitwork.data.remote.plan.mappers.toDomain
import com.br.xbizitwork.domain.model.plan.PlanModel
import com.br.xbizitwork.domain.model.plan.UserPlanModel
import javax.inject.Inject

class PlanRemoteDataSourceImpl @Inject constructor(
    private val planApiService: PlanApiService
): PlanRemoteDataSource {

    override suspend fun getAllPlans(): DefaultResult<List<PlanModel>> {
        return try {
            logInfo("PLAN_DATASOURCE", "📡 Chamando API getAllPlans")

            val apiResponse = planApiService.getAllPlans()

            if (!apiResponse.isSuccessful) {
                logInfo("PLAN_DATASOURCE", "❌ API retornou falha: ${apiResponse.message}")
                return DefaultResult.Error(message = apiResponse.message)
            }

            val responseData = apiResponse.data
            if (responseData == null) {
                logInfo("PLAN_DATASOURCE", "⚠️ API retornou data null")
                return DefaultResult.Success(emptyList())
            }

            val response = responseData.map { it.toDomain() }
            logInfo("PLAN_DATASOURCE", "✅ Response recebido: ${response.size} planos")

            DefaultResult.Success(response)

        } catch (e: Exception) {
            logInfo("PLAN_DATASOURCE", "❌ Erro: ${e.message}")
            DefaultResult.Error(message = e.message ?: "Erro desconhecido ao carregar planos")
        }
    }

    override suspend fun getAllPublicPlans(): DefaultResult<List<PlanModel>> {
        return try {
            logInfo("PLAN_DATASOURCE", "📡 Chamando API getAllPublicPlans")

            val apiResponse = planApiService.getAllPublicPlans()

            if (!apiResponse.isSuccessful) {
                logInfo("PLAN_DATASOURCE", "❌ API retornou falha: ${apiResponse.message}")
                return DefaultResult.Error(message = apiResponse.message)
            }

            val responseData = apiResponse.data
            if (responseData == null) {
                logInfo("PLAN_DATASOURCE", "⚠️ API retornou data null")
                return DefaultResult.Success(emptyList())
            }

            val response = responseData.map { it.toDomain() }
            logInfo("PLAN_DATASOURCE", "✅ Public Plans: ${response.size} planos")

            DefaultResult.Success(response)

        } catch (e: Exception) {
            logInfo("PLAN_DATASOURCE", "❌ Erro: ${e.message}")
            DefaultResult.Error(message = e.message ?: "Erro desconhecido ao carregar planos públicos")
        }
    }

    override suspend fun subscribeToPlan(userId: Int, planId: Int): DefaultResult<UserPlanModel> {
        return try {
            logInfo("PLAN_DATASOURCE", "📡 Chamando API subscribeToPlan - userId: $userId, planId: $planId")

            val request = SubscribePlanRequest(userId = userId, planId = planId)
            val apiResponse = planApiService.subscribeToPlan(request)

            if (!apiResponse.isSuccessful) {
                logInfo("PLAN_DATASOURCE", "❌ API retornou falha: ${apiResponse.message}")
                return DefaultResult.Error(message = apiResponse.message)
            }

            val responseData = apiResponse.data
            if (responseData == null) {
                logInfo("PLAN_DATASOURCE", "⚠️ API retornou data null")
                return DefaultResult.Error(message = "Erro ao assinar plano: resposta vazia")
            }

            val userPlan = responseData.toDomain()
            logInfo("PLAN_DATASOURCE", "✅ Plano assinado com sucesso: ${userPlan.id}")

            DefaultResult.Success(userPlan)

        } catch (e: Exception) {
            logInfo("PLAN_DATASOURCE", "❌ Erro: ${e.message}")
            DefaultResult.Error(message = e.message ?: "Erro desconhecido ao assinar plano")
        }
    }

    override suspend fun getUserCurrentPlan(): DefaultResult<UserPlanModel?> {
        return try {
            logInfo("PLAN_DATASOURCE", "📡 Chamando API getUserCurrentPlan (via token JWT)")

            val apiResponse = planApiService.getUserCurrentPlan()

            if (!apiResponse.isSuccessful) {
                logInfo("PLAN_DATASOURCE", "❌ API retornou falha: ${apiResponse.message}")
                return DefaultResult.Error(message = apiResponse.message)
            }

            val responseData = apiResponse.data

            // Se data é null, significa que usuário não tem plano ativo
            if (responseData == null) {
                logInfo("PLAN_DATASOURCE", "ℹ️ Usuário não possui plano ativo")
                return DefaultResult.Success(null)
            }

            val userPlan = responseData.toDomain()
            logInfo("PLAN_DATASOURCE", "✅ Plano ativo encontrado: planId=${userPlan.planId}")

            DefaultResult.Success(userPlan)

        } catch (e: Exception) {
            logInfo("PLAN_DATASOURCE", "❌ Erro: ${e.message}")
            DefaultResult.Error(message = e.message ?: "Erro desconhecido ao buscar plano atual")
        }
    }
}