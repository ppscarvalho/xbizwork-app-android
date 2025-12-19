package com.br.xbizitwork.data.di.subcategory

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.remote.subcategory.datasource.SubcategoryRemoteDataSource
import com.br.xbizitwork.data.repository.subcategory.SubcategoryRepositoryImpl
import com.br.xbizitwork.domain.repository.SubcategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubcategoryRepositoryModule {

    @Provides
    @Singleton
    fun provideSubcategoryRepository(
        remoteDataSource: SubcategoryRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): SubcategoryRepository {
        return SubcategoryRepositoryImpl(remoteDataSource, coroutineDispatcherProvider)
    }
}
