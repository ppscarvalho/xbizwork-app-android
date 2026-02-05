package com.br.xbizitwork.data.di.plan

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.remote.plan.datasource.PlanRemoteDataSource
import com.br.xbizitwork.data.repository.PlanRepositoryImpl
import com.br.xbizitwork.domain.repository.PlanRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Dagger Hilt para prover dependências de Repositório de Planos de Serviço
 * Segue o padrão do SkillsRepositoryModule
 */
@Module
@InstallIn(SingletonComponent::class)
object PlanRepositoryModule {

    @Provides
    @Singleton
    fun providePlanRepository(
        remoteDataSource: PlanRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): PlanRepository {
        return PlanRepositoryImpl(remoteDataSource, coroutineDispatcherProvider)
    }
}