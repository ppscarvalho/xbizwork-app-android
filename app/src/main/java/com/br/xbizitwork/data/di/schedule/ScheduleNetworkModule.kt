package com.br.xbizitwork.data.di.schedule

import com.br.xbizitwork.data.remote.schedule.api.ScheduleApiService
import com.br.xbizitwork.data.remote.schedule.api.ScheduleApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo DI para Schedule API
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleNetworkModule {
    
    @Binds
    @Singleton
    abstract fun bindScheduleApiService(
        impl: ScheduleApiServiceImpl
    ): ScheduleApiService
}
