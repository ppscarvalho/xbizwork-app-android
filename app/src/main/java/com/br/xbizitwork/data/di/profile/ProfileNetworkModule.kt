package com.br.xbizitwork.data.di.profile

import com.br.xbizitwork.data.remote.profile.api.ProfileApiService
import com.br.xbizitwork.data.remote.profile.api.ProfileApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileNetworkModule {

    @Provides
    @Singleton
    fun provideProfileApiService(
        httpClient: HttpClient
    ): ProfileApiService {
        return ProfileApiServiceImpl(httpClient)
    }
}

