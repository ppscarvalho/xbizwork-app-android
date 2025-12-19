package com.br.xbizitwork.data.di.schedule

import com.br.xbizitwork.data.remote.schedule.api.ScheduleApiService
import com.br.xbizitwork.data.remote.schedule.datasource.ScheduleRemoteDataSource
import com.br.xbizitwork.data.remote.schedule.datasource.ScheduleRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleRemoteModule {

    @Provides
    @Singleton
    fun provideScheduleRemoteDataSource(
        scheduleApiService: ScheduleApiService
    ): ScheduleRemoteDataSource {
        return ScheduleRemoteDataSourceImpl(
            scheduleApiService = scheduleApiService
        )
    }
}
