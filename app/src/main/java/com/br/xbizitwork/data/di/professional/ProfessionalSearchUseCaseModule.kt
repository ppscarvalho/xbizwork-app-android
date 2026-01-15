package com.br.xbizitwork.data.di.professional

import com.br.xbizitwork.domain.repository.ProfessionalRepository
import com.br.xbizitwork.domain.usecase.professional.SearchProfessionalsBySkillUseCase
import com.br.xbizitwork.domain.usecase.professional.SearchProfessionalsBySkillUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module to provide Professional Search UseCase dependencies
 * Following the same pattern as SkillsUseCaseModule
 */
@Module
@InstallIn(SingletonComponent::class)
object ProfessionalSearchUseCaseModule {

    @Provides
    @Singleton
    fun provideSearchProfessionalsBySkillUseCase(
        repository: ProfessionalRepository
    ): SearchProfessionalsBySkillUseCase {
        return SearchProfessionalsBySkillUseCaseImpl(repository)
    }
}
