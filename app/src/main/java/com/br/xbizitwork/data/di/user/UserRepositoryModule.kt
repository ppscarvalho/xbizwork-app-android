package com.br.xbizitwork.data.di.user

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.remote.user.datasource.UserRemoteDataSource
import com.br.xbizitwork.data.repository.UserRepositoryImpl
import com.br.xbizitwork.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSource: UserRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): UserRepository {
        return UserRepositoryImpl(
            remoteDataSource = remoteDataSource,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }
}