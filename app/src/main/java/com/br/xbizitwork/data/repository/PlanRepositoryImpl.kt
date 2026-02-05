package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.plan.datasource.PlanRemoteDataSource
import com.br.xbizitwork.domain.model.plan.PlanModel
import com.br.xbizitwork.domain.model.plan.UserPlanModel
import com.br.xbizitwork.domain.repository.PlanRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação do PlanRepository
 * Delega as operações para o RemoteDataSource
 * Segue o padrão do SkillsRepositoryImpl
 */
class PlanRepositoryImpl @Inject constructor(
    private val remoteDataSource: PlanRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : PlanRepository {
    /**
     * Obtém todos os planos de serviço
     *
     * @return DefaultResult com lista de planos de serviço
     */
    override suspend fun getAllPlans(): DefaultResult<List<PlanModel>> =
        withContext(coroutineDispatcherProvider.io()) {
            remoteDataSource.getAllPlans()
        }

    /**
     * Obtém todos os planos de serviço públicos
     *
     * @return DefaultResult com lista de planos de serviço públicos
     */
    override suspend fun getAllPublicPlans(): DefaultResult<List<PlanModel>> =
        withContext(coroutineDispatcherProvider.io()) {
            remoteDataSource.getAllPublicPlans()
        }

    /**
     * Inscreve um usuário em um plano
     *
     * @param userId ID do usuário
     * @param planId ID do plano
     * @return DefaultResult com os dados do plano do usuário
     */
    override suspend fun subscribeToPlan(userId: Int, planId: Int): DefaultResult<UserPlanModel> =
        withContext(coroutineDispatcherProvider.io()) {
            remoteDataSource.subscribeToPlan(userId, planId)
        }

    /**
     * Obtém o plano atual do usuário autenticado (via token JWT)
     *
     * @return DefaultResult com os dados do plano atual do usuário
     */
    override suspend fun getUserCurrentPlan(): DefaultResult<UserPlanModel?> =
        withContext(coroutineDispatcherProvider.io()) {
            remoteDataSource.getUserCurrentPlan()
        }
}
