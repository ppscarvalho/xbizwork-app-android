package com.br.xbizitwork.data.di.schedule

import com.br.xbizitwork.domain.repository.ScheduleRepository
import com.br.xbizitwork.domain.usecase.schedule.CreateScheduleUseCase
import com.br.xbizitwork.domain.usecase.schedule.CreateScheduleFromRequestUseCase
import com.br.xbizitwork.domain.usecase.schedule.DeleteScheduleUseCase
import com.br.xbizitwork.domain.usecase.schedule.GetAvailableTimeSlotsUseCase
import com.br.xbizitwork.domain.usecase.schedule.GetProfessionalSchedulesUseCase
import com.br.xbizitwork.domain.usecase.schedule.UpdateAvailabilityUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo DI para Schedule Use Cases
 */
@Module
@InstallIn(SingletonComponent::class)
object ScheduleUseCaseModule {
    
    @Provides
    @Singleton
    fun provideCreateScheduleUseCase(
        repository: ScheduleRepository
    ): CreateScheduleUseCase {
        return CreateScheduleUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideCreateScheduleFromRequestUseCase(
        repository: ScheduleRepository
    ): CreateScheduleFromRequestUseCase {
        return CreateScheduleFromRequestUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetProfessionalSchedulesUseCase(
        repository: ScheduleRepository
    ): GetProfessionalSchedulesUseCase {
        return GetProfessionalSchedulesUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideUpdateAvailabilityUseCase(
        repository: ScheduleRepository
    ): UpdateAvailabilityUseCase {
        return UpdateAvailabilityUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetAvailableTimeSlotsUseCase(
        repository: ScheduleRepository
    ): GetAvailableTimeSlotsUseCase {
        return GetAvailableTimeSlotsUseCase(repository)
    }
    
    @Provides
    @Singleton
    fun provideDeleteScheduleUseCase(
        repository: ScheduleRepository
    ): DeleteScheduleUseCase {
        return DeleteScheduleUseCase(repository)
    }
}
