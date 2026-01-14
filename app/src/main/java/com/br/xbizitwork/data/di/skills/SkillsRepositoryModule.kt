package com.br.xbizitwork.data.di.skills

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.repository.SkillsRepositoryImpl
import com.br.xbizitwork.domain.repository.SkillsRepository
import com.br.xbizitwork.domain.source.skills.SkillsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Dagger Hilt para prover dependências de Repository de Skills
 * Seguindo o mesmo padrão dos outros módulos de Repository
 */
@Module
@InstallIn(SingletonComponent::class)
object SkillsRepositoryModule {

    @Provides
    @Singleton
    fun provideSkillsRepository(
        remoteDataSource: SkillsRemoteDataSource,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): SkillsRepository {
        return SkillsRepositoryImpl(
            remoteDataSource = remoteDataSource,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }
}

