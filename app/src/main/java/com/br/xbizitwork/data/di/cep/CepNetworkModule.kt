package com.br.xbizitwork.data.di.cep

import com.br.xbizitwork.data.remote.cep.api.CepApiService
import com.br.xbizitwork.data.remote.cep.api.CepApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CepNetworkModule {

    @Provides
    @Singleton
    fun provideCepApiService(
        httpClient: HttpClient
    ): CepApiService {
        return CepApiServiceImpl(httpClient)
    }
}

