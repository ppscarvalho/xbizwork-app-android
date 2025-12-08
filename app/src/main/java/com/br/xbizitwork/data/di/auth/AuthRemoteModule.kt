package com.br.xbizitwork.data.di.auth

import com.br.xbizitwork.data.remote.auth.api.UserAuthApiService
import com.br.xbizitwork.data.remote.auth.datasource.UserAuthRemoteDataSourceImpl
import com.br.xbizitwork.data.remote.auth.datasource.UserAuthRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthRemoteModule {

    @Provides
    @Singleton
    fun provideUserAuthRemoteDataSource(
        authApiService: UserAuthApiService
    ): UserAuthRemoteDataSource {
        return UserAuthRemoteDataSourceImpl(authApiService)
    }
}