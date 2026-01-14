package com.br.xbizitwork.data.di.skills

import com.br.xbizitwork.data.remote.skills.api.SkillsApiService
import com.br.xbizitwork.data.remote.skills.api.SkillsApiServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

/**
 * Módulo Dagger Hilt para prover dependências de API de Skills
 * Seguindo o mesmo padrão dos outros módulos de API
 */
@Module
@InstallIn(SingletonComponent::class)
object SkillsApiModule {

    @Provides
    @Singleton
    fun provideSkillsApiService(httpClient: HttpClient): SkillsApiService {
        return SkillsApiServiceImpl(httpClient)
    }
}

