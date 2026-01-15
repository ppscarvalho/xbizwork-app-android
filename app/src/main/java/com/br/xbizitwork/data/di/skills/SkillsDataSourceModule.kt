package com.br.xbizitwork.data.di.skills

import com.br.xbizitwork.data.remote.skills.api.SkillsApiService
import com.br.xbizitwork.data.remote.skills.datasource.SkillsRemoteDataSourceImpl
import com.br.xbizitwork.data.remote.skills.datasource.SkillsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Dagger Hilt para prover dependências de DataSource de Skills
 * Seguindo o mesmo padrão dos outros módulos de DataSource
 */
@Module
@InstallIn(SingletonComponent::class)
object SkillsDataSourceModule {

    @Provides
    @Singleton
    fun provideSkillsRemoteDataSource(
        apiService: SkillsApiService
    ): SkillsRemoteDataSource {
        return SkillsRemoteDataSourceImpl(apiService)
    }
}

