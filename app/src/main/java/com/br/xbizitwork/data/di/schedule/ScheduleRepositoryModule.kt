package com.br.xbizitwork.data.di.schedule

import com.br.xbizitwork.data.repository.ScheduleRepositoryImpl
import com.br.xbizitwork.domain.repository.ScheduleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo DI para Schedule Repository
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleRepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindScheduleRepository(
        impl: ScheduleRepositoryImpl
    ): ScheduleRepository
}
