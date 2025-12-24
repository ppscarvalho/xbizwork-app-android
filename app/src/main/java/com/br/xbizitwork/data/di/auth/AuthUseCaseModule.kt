package com.br.xbizitwork.data.di.auth

import com.br.xbizitwork.domain.usecase.auth.changepassword.ChangePasswordUseCase
import com.br.xbizitwork.domain.usecase.auth.changepassword.ChangePasswordUseCaseImpl
import com.br.xbizitwork.domain.usecase.auth.signin.SignInUseCase
import com.br.xbizitwork.domain.usecase.auth.signin.SignInUseCaseImpl
import com.br.xbizitwork.domain.usecase.auth.signup.SignUpUseCase
import com.br.xbizitwork.domain.usecase.auth.signup.SignUpUseCaseImpl
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCase
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCaseImpl
import com.br.xbizitwork.domain.usecase.session.RemoveAuthSessionUseCase
import com.br.xbizitwork.domain.usecase.session.RemoveAuthSessionUseCaseImpl
import com.br.xbizitwork.domain.usecase.session.SaveAuthSessionUseCase
import com.br.xbizitwork.domain.usecase.session.SaveAuthSessionUseCaseImpl
import com.br.xbizitwork.domain.repository.UserAuthRepository
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
    @Singleton
    fun provideChangePasswordUseCase(
        authRepository: UserAuthRepository,
    ): ChangePasswordUseCase {
        return ChangePasswordUseCaseImpl(
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
