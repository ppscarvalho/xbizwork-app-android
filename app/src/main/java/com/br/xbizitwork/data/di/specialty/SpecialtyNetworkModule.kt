package com.br.xbizitwork.data.di.specialty

import com.br.xbizitwork.data.remote.specialty.api.SpecialtyApiService
import com.br.xbizitwork.data.remote.specialty.api.SpecialtyApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpecialtyNetworkModule {
    
    @Provides
    @Singleton
    fun provideSpecialtyApiService(httpClient: HttpClient): SpecialtyApiService {
        return SpecialtyApiServiceImpl(httpClient)
    }
}
