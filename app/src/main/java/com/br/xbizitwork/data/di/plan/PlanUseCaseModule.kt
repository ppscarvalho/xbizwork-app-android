package com.br.xbizitwork.data.di.plan

import com.br.xbizitwork.domain.repository.PlanRepository
import com.br.xbizitwork.domain.usecase.plan.GetAllPlanUseCase
import com.br.xbizitwork.domain.usecase.plan.GetAllPlanUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

/**
 * Módulo Dagger Hilt para prover dependências de UseCase de Planos de Serviço
 * Segue o padrão do SkillsUseCaseModule
 */
@Module
@InstallIn(SingletonComponent::class)
object PlanUseCaseModule {

    @Provides
    @Singleton
    fun provideGetAllPlanUseCase(
        planRepository: PlanRepository
    ): GetAllPlanUseCase {
        return GetAllPlanUseCaseImpl(planRepository)
    }
}