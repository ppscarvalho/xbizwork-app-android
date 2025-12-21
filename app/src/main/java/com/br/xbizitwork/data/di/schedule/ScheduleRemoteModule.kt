package com.br.xbizitwork.data.di.schedule

import com.br.xbizitwork.data.remote.schedule.datasource.ScheduleRemoteDataSource
import com.br.xbizitwork.data.remote.schedule.datasource.ScheduleRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo DI para Schedule Remote Data Source
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ScheduleRemoteModule {
    
    @Binds
    @Singleton
    abstract fun bindScheduleRemoteDataSource(
        impl: ScheduleRemoteDataSourceImpl
    ): ScheduleRemoteDataSource
}
