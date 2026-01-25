package com.br.xbizitwork.data.di.faq

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.remote.faq.datasource.FaqRemoteDataSource
import com.br.xbizitwork.data.repository.FaqRepositoryImpl
import com.br.xbizitwork.domain.repository.FaqRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing FAQ Repository dependencies
 * Following the same pattern as other Repository modules
 */
@Module
@InstallIn(SingletonComponent::class)
object FaqRepositoryModule {

    @Provides
    @Singleton
    fun provideFaqRepository(
        remoteDataSource: FaqRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): FaqRepository {
        return FaqRepositoryImpl(remoteDataSource, coroutineDispatcherProvider)
    }
}
