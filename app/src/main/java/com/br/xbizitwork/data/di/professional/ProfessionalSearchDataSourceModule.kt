package com.br.xbizitwork.data.di.professional

import com.br.xbizitwork.data.remote.professional.api.ProfessionalSearchApiService
import com.br.xbizitwork.data.remote.professional.datasource.ProfessionalSearchRemoteDataSource
import com.br.xbizitwork.data.remote.professional.datasource.ProfessionalSearchRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module to provide Professional Search DataSource dependencies
 * Following the same pattern as SkillsDataSourceModule
 */
@Module
@InstallIn(SingletonComponent::class)
object ProfessionalSearchDataSourceModule {

    @Provides
    @Singleton
    fun provideProfessionalSearchRemoteDataSource(
        apiService: ProfessionalSearchApiService
    ): ProfessionalSearchRemoteDataSource {
        return ProfessionalSearchRemoteDataSourceImpl(apiService)
    }
}
