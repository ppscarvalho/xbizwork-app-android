package com.br.xbizitwork.data.di.schedule

import com.br.xbizitwork.domain.usecase.schedule.ValidateScheduleUseCase
import com.br.xbizitwork.domain.usecase.schedule.ValidateScheduleUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleValidationModule {

    @Provides
    @Singleton
    fun provideValidateScheduleUseCase(): ValidateScheduleUseCase {
        return ValidateScheduleUseCaseImpl()
    }
}

