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
 * Módulo Dagger Hilt para prover dependências de DataSource de FAQ
 * Segue o padrão do SkillsDataSourceModule
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
