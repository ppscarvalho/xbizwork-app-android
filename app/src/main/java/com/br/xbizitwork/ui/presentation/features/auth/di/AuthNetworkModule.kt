package com.br.xbizitwork.ui.presentation.features.auth.di

import com.br.xbizitwork.ui.presentation.features.auth.data.remote.api.UserAuthApiService
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.api.UserAuthApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import io.ktor.client.HttpClient

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