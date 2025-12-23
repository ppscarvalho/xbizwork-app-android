package com.br.xbizitwork.data.di.schedule

import com.br.xbizitwork.data.remote.schedule.api.ScheduleApiService
import com.br.xbizitwork.data.remote.schedule.api.ScheduleApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleNetworkModule {
    @Provides
    @Singleton
    fun provideScheduleApiService(
        httpClient: HttpClient
    ): ScheduleApiService {
        return ScheduleApiServiceImpl(httpClient = httpClient)
    }
}
