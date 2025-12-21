package com.br.xbizitwork.data.di.specialty

import com.br.xbizitwork.domain.repository.SpecialtyRepository
import com.br.xbizitwork.domain.usecase.specialty.GetSpecialtiesByCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpecialtyUseCaseModule {
    
    @Provides
    @Singleton
    fun provideGetSpecialtiesByCategoryUseCase(
        repository: SpecialtyRepository
    ): GetSpecialtiesByCategoryUseCase {
        return GetSpecialtiesByCategoryUseCase(repository)
    }
}
