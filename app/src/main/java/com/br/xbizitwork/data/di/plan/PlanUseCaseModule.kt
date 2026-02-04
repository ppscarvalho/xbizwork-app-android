package com.br.xbizitwork.data.di.plan

import com.br.xbizitwork.domain.repository.PlanRepository
import com.br.xbizitwork.domain.usecase.plan.GetAllPlanUseCase
import com.br.xbizitwork.domain.usecase.plan.GetAllPlanUseCaseImpl
import com.br.xbizitwork.domain.usecase.plan.GetAllPublicPlanUseCase
import com.br.xbizitwork.domain.usecase.plan.GetAllPublicPlanUseCaseImpl
import com.br.xbizitwork.domain.usecase.plan.GetUserCurrentPlanUseCase
import com.br.xbizitwork.domain.usecase.plan.GetUserCurrentPlanUseCaseImpl
import com.br.xbizitwork.domain.usecase.plan.SubscribeToPlanUseCase
import com.br.xbizitwork.domain.usecase.plan.SubscribeToPlanUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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

    @Provides
    @Singleton
    fun provideGetAllPublicPlanUseCase(
        planRepository: PlanRepository
    ): GetAllPublicPlanUseCase {
        return GetAllPublicPlanUseCaseImpl(planRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserCurrentPlanUseCase(
        planRepository: PlanRepository
    ): GetUserCurrentPlanUseCase {
        return GetUserCurrentPlanUseCaseImpl(planRepository)
    }

    @Provides
    @Singleton
    fun provideSubscribeToPlanUseCase(
        planRepository: PlanRepository
    ): SubscribeToPlanUseCase {
        return SubscribeToPlanUseCaseImpl(planRepository)
    }
}