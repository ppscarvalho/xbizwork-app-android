package com.br.xbizitwork.data.di.skills

import com.br.xbizitwork.domain.repository.SkillsRepository
import com.br.xbizitwork.domain.usecase.skills.GetUserSkillsUseCase
import com.br.xbizitwork.domain.usecase.skills.GetUserSkillsUseCaseImpl
import com.br.xbizitwork.domain.usecase.skills.SaveUserSkillsUseCase
import com.br.xbizitwork.domain.usecase.skills.SaveUserSkillsUseCaseImpl
import com.br.xbizitwork.domain.usecase.skills.SearchProfessionalBySkillUseCase
import com.br.xbizitwork.domain.usecase.skills.SearchProfessionalBySkillUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Dagger Hilt para prover dependências de UseCases de Skills
 * Seguindo o mesmo padrão dos outros módulos de UseCase
 */
@Module
@InstallIn(SingletonComponent::class)
object SkillsUseCaseModule {

    @Provides
    @Singleton
    fun provideSaveUserSkillsUseCase(
        repository: SkillsRepository
    ): SaveUserSkillsUseCase {
        return SaveUserSkillsUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideGetUserSkillsUseCase(
        repository: SkillsRepository
    ): GetUserSkillsUseCase {
        return GetUserSkillsUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideSearchProfessionalBySkillUseCase(
        repository: SkillsRepository
    ): SearchProfessionalBySkillUseCase {
        return SearchProfessionalBySkillUseCaseImpl(repository)
    }
}

