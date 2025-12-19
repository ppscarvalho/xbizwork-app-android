package com.br.xbizitwork.data.di.subcategory

import com.br.xbizitwork.data.remote.subcategory.api.SubcategoryApiService
import com.br.xbizitwork.data.remote.subcategory.api.SubcategoryApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SubcategoryNetworkModule {

    @Provides
    @Singleton
    fun provideSubcategoryApiService(
        httpClient: HttpClient
    ): SubcategoryApiService {
        return SubcategoryApiServiceImpl(
            httpClient = httpClient
        )
    }
}
