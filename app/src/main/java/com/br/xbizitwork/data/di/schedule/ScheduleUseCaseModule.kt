package com.br.xbizitwork.data.di.schedule

import com.br.xbizitwork.domain.repository.ScheduleRepository
import com.br.xbizitwork.domain.usecase.schedule.CreateScheduleUseCase
import com.br.xbizitwork.domain.usecase.schedule.CreateScheduleUseCaseImpl
import com.br.xbizitwork.domain.usecase.schedule.CreateScheduleFromRequestUseCase
import com.br.xbizitwork.domain.usecase.schedule.CreateScheduleFromRequestUseCaseImpl
import com.br.xbizitwork.domain.usecase.schedule.DeleteScheduleUseCase
import com.br.xbizitwork.domain.usecase.schedule.DeleteScheduleUseCaseImpl
import com.br.xbizitwork.domain.usecase.schedule.GetAvailableTimeSlotsUseCase
import com.br.xbizitwork.domain.usecase.schedule.GetAvailableTimeSlotsUseCaseImpl
import com.br.xbizitwork.domain.usecase.schedule.SearchScheduleUseCase
import com.br.xbizitwork.domain.usecase.schedule.SearchScheduleUseCaseImpl
import com.br.xbizitwork.domain.usecase.schedule.GetProfessionalSchedulesUseCase
import com.br.xbizitwork.domain.usecase.schedule.GetProfessionalSchedulesUseCaseImpl
import com.br.xbizitwork.domain.usecase.schedule.UpdateAvailabilityUseCase
import com.br.xbizitwork.domain.usecase.schedule.UpdateAvailabilityUseCaseImpl
import com.br.xbizitwork.domain.usecase.schedule.ValidateScheduleOnBackendUseCase
import com.br.xbizitwork.domain.usecase.schedule.ValidateScheduleOnBackendUseCaseImpl
import com.br.xbizitwork.domain.usecase.schedule.ValidateScheduleTimeSlotUseCase
import com.br.xbizitwork.domain.usecase.schedule.ValidateScheduleTimeSlotUseCaseImpl
import com.br.xbizitwork.domain.usecase.schedule.ValidateScheduleUseCase
import com.br.xbizitwork.domain.usecase.schedule.ValidateScheduleUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleUseCaseModule {
    
    @Provides
    @Singleton
    fun provideCreateScheduleUseCase(
        repository: ScheduleRepository
    ): CreateScheduleUseCase {
        return CreateScheduleUseCaseImpl(repository)
    }
    
    @Provides
    @Singleton
    fun provideCreateScheduleFromRequestUseCase(
        repository: ScheduleRepository
    ): CreateScheduleFromRequestUseCase {
        return CreateScheduleFromRequestUseCaseImpl(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetProfessionalSchedulesUseCase(
        repository: ScheduleRepository
    ): GetProfessionalSchedulesUseCase {
        return GetProfessionalSchedulesUseCaseImpl(repository)
    }
    
    @Provides
    @Singleton
    fun provideUpdateAvailabilityUseCase(
        repository: ScheduleRepository
    ): UpdateAvailabilityUseCase {
        return UpdateAvailabilityUseCaseImpl(repository)
    }
    
    @Provides
    @Singleton
    fun provideGetAvailableTimeSlotsUseCase(
        repository: ScheduleRepository
    ): GetAvailableTimeSlotsUseCase {
        return GetAvailableTimeSlotsUseCaseImpl(repository)
    }
    
    @Provides
    @Singleton
    fun provideDeleteScheduleUseCase(
        repository: ScheduleRepository
    ): DeleteScheduleUseCase {
        return DeleteScheduleUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideValidateScheduleOnBackendUseCase(
        repository: ScheduleRepository
    ): ValidateScheduleOnBackendUseCase {
        return ValidateScheduleOnBackendUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideValidateScheduleTimeSlotUseCase(): ValidateScheduleTimeSlotUseCase {
        return ValidateScheduleTimeSlotUseCaseImpl()
    }

    @Provides
    @Singleton
    fun provideValidateScheduleUseCase(): ValidateScheduleUseCase {
        return ValidateScheduleUseCaseImpl()
    }

    @Provides
    @Singleton
    fun provideSearchScheduleUseCase(
        repository: ScheduleRepository
    ): SearchScheduleUseCase {
        return SearchScheduleUseCaseImpl(repository)
    }
}
