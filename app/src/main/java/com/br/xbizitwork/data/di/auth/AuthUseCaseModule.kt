package com.br.xbizitwork.data.di.auth

import com.br.xbizitwork.application.usecase.auth.SignInUseCase
import com.br.xbizitwork.application.usecase.auth.SignInUseCaseImpl
import com.br.xbizitwork.application.usecase.auth.SignUpUseCase
import com.br.xbizitwork.application.usecase.auth.SignUpUseCaseImpl
import com.br.xbizitwork.application.usecase.session.GetAuthSessionUseCase
import com.br.xbizitwork.application.usecase.session.GetAuthSessionUseCaseImpl
import com.br.xbizitwork.application.usecase.session.RemoveAuthSessionUseCase
import com.br.xbizitwork.application.usecase.session.RemoveAuthSessionUseCaseImpl
import com.br.xbizitwork.application.usecase.session.SaveAuthSessionUseCase
import com.br.xbizitwork.application.usecase.session.SaveAuthSessionUseCaseImpl
import com.br.xbizitwork.domain.repository.auth.UserAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthUseCaseModule {

    @Provides
    @Singleton
    fun provideSignUpUseCase(
        authRepository: UserAuthRepository,
    ): SignUpUseCase {
        return SignUpUseCaseImpl(
            authRepository = authRepository
        )
    }

    @Provides
    @Singleton
    fun provideSignInUseCase(
        authRepository: UserAuthRepository,
    ): SignInUseCase {
        return SignInUseCaseImpl(
            authRepository = authRepository
        )
    }

    @Provides
    fun provideGetAuthSessionUseCase(
        authRepository: UserAuthRepository
    ): GetAuthSessionUseCase {
        return GetAuthSessionUseCaseImpl(authRepository)
    }

    @Provides
    fun provideSaveAuthSessionUseCase(
        authRepository: UserAuthRepository
    ): SaveAuthSessionUseCase {
        return SaveAuthSessionUseCaseImpl(authRepository = authRepository)
    }

    @Provides
    fun provideRemoveAuthSessionUseCase(
        authRepository: UserAuthRepository
    ): RemoveAuthSessionUseCase {
        return RemoveAuthSessionUseCaseImpl(authRepository)
    }
}
