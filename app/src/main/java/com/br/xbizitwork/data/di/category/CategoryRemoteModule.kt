package com.br.xbizitwork.data.di.category

import com.br.xbizitwork.data.remote.category.api.CategoryApiService
import com.br.xbizitwork.data.remote.category.datasource.CategoryRemoteDataSource
import com.br.xbizitwork.data.remote.category.datasource.CategoryRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryRemoteModule {

    @Provides
    @Singleton
    fun provideCategoryRemoteDataSource(
        categoryApiService: CategoryApiService
    ): CategoryRemoteDataSource{
        return CategoryRemoteDataSourceImpl(
            categoryApiService = categoryApiService
        )
    }
}