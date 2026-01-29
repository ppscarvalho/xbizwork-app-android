package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.plan.datasource.PlanRemoteDataSource
import com.br.xbizitwork.domain.model.plan.PlanModel
import com.br.xbizitwork.domain.repository.PlanRepository
import jakarta.inject.Inject
import kotlinx.coroutines.withContext

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
}
