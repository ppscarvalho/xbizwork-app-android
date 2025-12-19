package com.br.xbizitwork.data.di.schedule

import com.br.xbizitwork.domain.repository.ScheduleRepository
import com.br.xbizitwork.domain.usecase.schedule.SaveScheduleUseCase
import com.br.xbizitwork.domain.usecase.schedule.SaveScheduleUseCaseImpl
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
    fun provideSaveScheduleUseCase(
        repository: ScheduleRepository
    ): SaveScheduleUseCase {
        return SaveScheduleUseCaseImpl(repository)
    }
}
