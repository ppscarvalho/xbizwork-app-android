package com.br.xbizitwork.data.di.profile

import com.br.xbizitwork.domain.usecase.profile.ValidateProfileUseCase
import com.br.xbizitwork.domain.usecase.profile.ValidateProfileUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileValidationModule {

    @Provides
    @Singleton
    fun provideValidateProfileUseCase(): ValidateProfileUseCase {
        return ValidateProfileUseCaseImpl()
    }
}

