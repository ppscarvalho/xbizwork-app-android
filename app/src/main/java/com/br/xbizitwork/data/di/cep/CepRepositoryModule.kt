package com.br.xbizitwork.data.di.cep

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.repository.CepRepositoryImpl
import com.br.xbizitwork.domain.repository.CepRepository
import com.br.xbizitwork.data.remote.cep.datasource.CepRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CepRepositoryModule {

    @Provides
    @Singleton
    fun provideCepRepository(
        remoteDataSource: CepRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): CepRepository {
        return CepRepositoryImpl(
            remoteDataSource = remoteDataSource,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }
}

