package com.br.xbizitwork.data.di.category

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.remote.category.datasource.CategoryRemoteDataSource
import com.br.xbizitwork.data.repository.CategoryRepositoryImpl
import com.br.xbizitwork.domain.repository.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryRepositoryModule {

    @Provides
    @Singleton
    fun provideCategoryRepository(
        remoteDataSource: CategoryRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ) : CategoryRepository{
        return CategoryRepositoryImpl(remoteDataSource, coroutineDispatcherProvider)
    }
}