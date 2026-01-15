package com.br.xbizitwork.data.di.professional

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.remote.professional.datasource.ProfessionalSearchRemoteDataSource
import com.br.xbizitwork.data.repository.ProfessionalRepositoryImpl
import com.br.xbizitwork.domain.repository.ProfessionalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module to provide Professional Repository dependencies
 * Following the same pattern as SkillsRepositoryModule
 */
@Module
@InstallIn(SingletonComponent::class)
object ProfessionalRepositoryModule {

    @Provides
    @Singleton
    fun provideProfessionalRepository(
        searchRemoteDataSource: ProfessionalSearchRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): ProfessionalRepository {
        return ProfessionalRepositoryImpl(
            searchRemoteDataSource = searchRemoteDataSource,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }
}
