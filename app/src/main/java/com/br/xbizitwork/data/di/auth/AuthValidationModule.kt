package com.br.xbizitwork.data.di.auth

import com.br.xbizitwork.domain.usecase.auth.signin.ValidateSignInUseCase
import com.br.xbizitwork.domain.usecase.auth.signin.ValidateSignInUseCaseImpl
import com.br.xbizitwork.domain.usecase.auth.signup.ValidateSignUpUseCase
import com.br.xbizitwork.domain.usecase.auth.signup.ValidateSignUpUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthValidationModule {

    @Provides
    @Singleton
    fun provideValidateSignUpUseCase(): ValidateSignUpUseCase {
        return ValidateSignUpUseCaseImpl()
    }

    @Provides
    @Singleton
    fun provideValidateSignInUseCase(): ValidateSignInUseCase {
        return ValidateSignInUseCaseImpl()
    }
}
