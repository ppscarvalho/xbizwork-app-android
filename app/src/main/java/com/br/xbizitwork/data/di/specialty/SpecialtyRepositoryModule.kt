package com.br.xbizitwork.data.di.specialty

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.remote.specialty.datasource.SpecialtyRemoteDataSource
import com.br.xbizitwork.data.repository.SpecialtyRepositoryImpl
import com.br.xbizitwork.domain.repository.SpecialtyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpecialtyRepositoryModule {

    @Provides
    @Singleton
    fun provideSpecialtyRepository(
        remoteDataSource: SpecialtyRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): SpecialtyRepository {
        return SpecialtyRepositoryImpl(
            remoteDataSource = remoteDataSource,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }
}
