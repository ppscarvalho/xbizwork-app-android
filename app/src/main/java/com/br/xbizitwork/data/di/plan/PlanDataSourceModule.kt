package com.br.xbizitwork.data.di.plan

import com.br.xbizitwork.data.remote.plan.api.PlanApiService
import com.br.xbizitwork.data.remote.plan.datasource.PlanRemoteDataSource
import com.br.xbizitwork.data.remote.plan.datasource.PlanRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Dagger Hilt para prover dependências de DataSource de Planos de Serviço
 * Segue o padrão do SkillsDataSourceModule
 */
@Module
@InstallIn(SingletonComponent::class)
object PlanDataSourceModule {

    @Provides
    @Singleton
    fun providePlanRemoteDataSource(
        planApiService: PlanApiService
    ): PlanRemoteDataSource {
       return PlanRemoteDataSourceImpl(planApiService)
    }
}