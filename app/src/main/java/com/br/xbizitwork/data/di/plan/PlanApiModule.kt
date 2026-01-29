package com.br.xbizitwork.data.di.plan

import com.br.xbizitwork.data.remote.plan.api.PlanApiService
import com.br.xbizitwork.data.remote.plan.api.PlanServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import jakarta.inject.Singleton

/**
 * Módulo Dagger Hilt para prover dependências de API de Planos de Serviço
 * Segue o padrão do SkillsApiModule
 * */
@Module
@InstallIn(SingletonComponent::class)
object PlanApiModule {

    @Provides
    @Singleton
    fun providePlanApiService(httpClient: HttpClient): PlanApiService {
        return PlanServiceImpl(httpClient)
   }
}