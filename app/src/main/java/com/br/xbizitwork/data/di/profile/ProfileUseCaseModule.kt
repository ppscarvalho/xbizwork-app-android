package com.br.xbizitwork.data.di.profile

import com.br.xbizitwork.domain.repository.ProfileRepository
import com.br.xbizitwork.domain.usecase.profile.UpdateProfileUseCase
import com.br.xbizitwork.domain.usecase.profile.UpdateProfileUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileUseCaseModule {

    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(
        repository: ProfileRepository
    ): UpdateProfileUseCase {
        return UpdateProfileUseCaseImpl(repository = repository)
    }
}

