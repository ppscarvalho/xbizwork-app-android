package com.br.xbizitwork.data.di.profile

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.repository.ProfileRepositoryImpl
import com.br.xbizitwork.domain.repository.ProfileRepository
import com.br.xbizitwork.domain.source.profile.ProfileRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileRepositoryModule {

    @Provides
    @Singleton
    fun provideProfileRepository(
        remoteDataSource: ProfileRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): ProfileRepository {
        return ProfileRepositoryImpl(
            remoteDataSource = remoteDataSource,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }
}

