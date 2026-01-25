package com.br.xbizitwork.data.di.faq

import com.br.xbizitwork.domain.repository.FaqRepository
import com.br.xbizitwork.domain.usecase.faq.GetPublicFaqSectionsUseCase
import com.br.xbizitwork.domain.usecase.faq.GetPublicFaqSectionsUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger Hilt module for providing FAQ UseCase dependencies
 * Following the same pattern as SkillsUseCaseModule
 */
@Module
@InstallIn(SingletonComponent::class)
object FaqUseCaseModule {

    @Provides
    @Singleton
    fun provideGetPublicFaqSectionsUseCase(
        repository: FaqRepository
    ): GetPublicFaqSectionsUseCase {
        return GetPublicFaqSectionsUseCaseImpl(repository)
    }
}
