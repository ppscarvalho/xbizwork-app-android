package com.br.xbizitwork.data.di.category

import com.br.xbizitwork.data.remote.category.api.CategoryApiService
import com.br.xbizitwork.data.remote.category.api.CategoryApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryNetworkModule {

    @Provides
    @Singleton
    fun provideCategoryApiService(
        httpClient: HttpClient
    ) : CategoryApiService{
        return CategoryApiServiceImpl(
            httpClient = httpClient
        )
    }

}