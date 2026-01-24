package com.br.xbizitwork.data.di.professional

import com.br.xbizitwork.data.remote.searchprofessional.api.ProfessionalSearchApiService
import com.br.xbizitwork.data.remote.searchprofessional.api.ProfessionalSearchApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

/**
 * Dagger Hilt module to provide Professional Search API dependencies
 * Following the same pattern as SkillsApiModule
 */
@Module
@InstallIn(SingletonComponent::class)
object ProfessionalSearchApiModule {

    @Provides
    @Singleton
    fun provideProfessionalSearchApiService(httpClient: HttpClient): ProfessionalSearchApiService {
        return ProfessionalSearchApiServiceImpl(httpClient)
    }
}
