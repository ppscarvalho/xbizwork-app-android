package com.br.xbizitwork.data.di.subcategory

import com.br.xbizitwork.data.remote.subcategory.api.SubcategoryApiService
import com.br.xbizitwork.data.remote.subcategory.datasource.SubcategoryRemoteDataSource
import com.br.xbizitwork.data.remote.subcategory.datasource.SubcategoryRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubcategoryRemoteModule {

    @Provides
    @Singleton
    fun provideSubcategoryRemoteDataSource(
        subcategoryApiService: SubcategoryApiService
    ): SubcategoryRemoteDataSource {
        return SubcategoryRemoteDataSourceImpl(
            subcategoryApiService = subcategoryApiService
        )
    }
}
