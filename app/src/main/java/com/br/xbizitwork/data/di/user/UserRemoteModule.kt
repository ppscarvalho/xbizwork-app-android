package com.br.xbizitwork.data.di.user

import com.br.xbizitwork.data.remote.user.api.UserApiService
import com.br.xbizitwork.data.remote.user.datasource.UserRemoteDataSource
import com.br.xbizitwork.data.remote.user.datasource.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRemoteModule {
    @Provides
    @Singleton
    fun provideUserRemoteDataSource(
        apiService: UserApiService
    ): UserRemoteDataSource {
        return UserRemoteDataSourceImpl(apiService)
    }
}
