package com.br.xbizitwork.data.di.faq

import com.br.xbizitwork.data.remote.faq.api.FaqApiService
import com.br.xbizitwork.data.remote.faq.api.FaqApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing FAQ API dependencies
 * Following the same pattern as SkillsApiModule
 */
@Module
@InstallIn(SingletonComponent::class)
object FaqApiModule {

    @Provides
    @Singleton
    fun provideFaqApiService(httpClient: HttpClient): FaqApiService {
        return FaqApiServiceImpl(httpClient)
    }
}
