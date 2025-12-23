package com.br.xbizitwork.data.di.cep

import com.br.xbizitwork.data.remote.cep.api.CepApiService
import com.br.xbizitwork.data.remote.cep.datasource.CepRemoteDataSourceImpl
import com.br.xbizitwork.domain.source.cep.CepRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CepRemoteModule {

    @Provides
    @Singleton
    fun provideCepRemoteDataSource(
        cepApiService: CepApiService
    ): CepRemoteDataSource {
        return CepRemoteDataSourceImpl(cepApiService)
    }
}

