package com.br.xbizitwork.data.di.faq

import com.br.xbizitwork.data.remote.faq.api.FaqApiService
import com.br.xbizitwork.data.remote.faq.datasource.FaqRemoteDataSource
import com.br.xbizitwork.data.remote.faq.datasource.FaqRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing FAQ DataSource dependencies
 * Following the same pattern as other DataSource modules
 */
@Module
@InstallIn(SingletonComponent::class)
object FaqDataSourceModule {

    @Provides
    @Singleton
    fun provideFaqRemoteDataSource(
        apiService: FaqApiService
    ): FaqRemoteDataSource {
        return FaqRemoteDataSourceImpl(apiService)
    }
}
