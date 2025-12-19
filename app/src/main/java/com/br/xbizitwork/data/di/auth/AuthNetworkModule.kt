package com.br.xbizitwork.data.di.auth

import com.br.xbizitwork.data.remote.auth.api.UserAuthApiService
import com.br.xbizitwork.data.remote.auth.api.UserAuthApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthNetworkModule {
    @Provides
    @Singleton
    fun provideUserAuthApiService(
        httpClient: HttpClient
    ): UserAuthApiService {
        return UserAuthApiServiceImpl(httpClient = httpClient)
    }
}