package com.br.xbizitwork.data.di.faq

import com.br.xbizitwork.domain.repository.FaqRepository
import com.br.xbizitwork.domain.usecase.faq.GetFaqSectionsUseCase
import com.br.xbizitwork.domain.usecase.faq.GetFaqSectionsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Módulo Dagger Hilt para prover dependências de UseCases de FAQ
 * Seguindo o mesmo padrão do SkillsUseCaseModule
 */
@Module
@InstallIn(SingletonComponent::class)
object FaqUseCaseModule {

    @Provides
    @Singleton
    fun provideGetFaqSectionsUseCase(
        repository: FaqRepository
    ): GetFaqSectionsUseCase {
        return GetFaqSectionsUseCaseImpl(repository)
    }
}
