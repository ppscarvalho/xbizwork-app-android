package com.br.xbizitwork.data.di.schedule

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.remote.schedule.datasource.ScheduleRemoteDataSource
import com.br.xbizitwork.data.repository.ScheduleRepositoryImpl
import com.br.xbizitwork.domain.repository.ScheduleRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScheduleRepositoryModule {
    @Provides
    @Singleton
    fun provideScheduleRepository(
        remoteDataSource: ScheduleRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): ScheduleRepository {
        return ScheduleRepositoryImpl(
            remoteDataSource = remoteDataSource,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }
}