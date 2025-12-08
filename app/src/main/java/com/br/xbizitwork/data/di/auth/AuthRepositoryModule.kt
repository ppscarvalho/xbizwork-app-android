package com.br.xbizitwork.data.di.auth

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.local.auth.datastore.interfaces.AuthSessionLocalDataSource
import com.br.xbizitwork.data.remote.auth.datasource.UserAuthRemoteDataSource
import com.br.xbizitwork.data.repository.UserAuthRepositoryImpl
import com.br.xbizitwork.domain.repository.UserAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRepositoryModule {

    @Provides
    @Singleton
    fun provideUserAuthRepository(
        remoteDataSource: UserAuthRemoteDataSource,
        localDataSource: AuthSessionLocalDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): UserAuthRepository {
        return UserAuthRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }
}
