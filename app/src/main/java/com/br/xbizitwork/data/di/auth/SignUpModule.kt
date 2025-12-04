package com.br.xbizitwork.data.di.auth

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.data.local.auth.datastore.AuthSessionLocalDataSource
import com.br.xbizitwork.data.remote.auth.api.UserAuthApiService
import com.br.xbizitwork.data.remote.auth.source.UserAuthRemoteDataSource
import com.br.xbizitwork.data.remote.auth.source.UserAuthRemoteDataSourceImpl
import com.br.xbizitwork.data.repository.auth.UserAuthRepositoryImpl
import com.br.xbizitwork.domain.repository.auth.UserAuthRepository
import com.br.xbizitwork.domain.usecase.auth.local.GetAuthSessionUseCase
import com.br.xbizitwork.domain.usecase.auth.local.GetAuthSessionUseCaseImpl
import com.br.xbizitwork.domain.usecase.auth.local.RemoveAuthSessionUseCase
import com.br.xbizitwork.domain.usecase.auth.local.RemoveAuthSessionUseCaseImpl
import com.br.xbizitwork.domain.usecase.auth.local.SaveAuthSessionUseCase
import com.br.xbizitwork.domain.usecase.auth.local.SaveAuthSessionUseCaseImpl
import com.br.xbizitwork.domain.usecase.auth.sign.SignInUseCase
import com.br.xbizitwork.domain.usecase.auth.sign.SignInUseCaseImpl
import com.br.xbizitwork.domain.usecase.auth.sign.ValidateSignInUseCase
import com.br.xbizitwork.domain.usecase.auth.sign.ValidateSignInUseCaseImpl
import com.br.xbizitwork.domain.usecase.auth.signup.SignUpUseCase
import com.br.xbizitwork.domain.usecase.auth.signup.SignUpUseCaseImpl
import com.br.xbizitwork.domain.usecase.auth.signup.ValidateSignUpUseCase
import com.br.xbizitwork.domain.usecase.auth.signup.ValidateSignUpUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SignUpModule {

    @Provides
    @Singleton
    fun provideUserAuthRemoteDataSource(
        authApiService: UserAuthApiService
    ) : UserAuthRemoteDataSource {
        return UserAuthRemoteDataSourceImpl(authApiService = authApiService)
    }

    @Provides
    @Singleton
    fun provideUserAuthRepository(
        remoteDataSource: UserAuthRemoteDataSource,
        localDataSource: AuthSessionLocalDataSource
    ): UserAuthRepository {
        return UserAuthRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(
        authRepository: UserAuthRepository,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): SignUpUseCase {
        return SignUpUseCaseImpl(
            authRepository = authRepository,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }

    @Provides
    @Singleton
    fun provideSignInUseCase(
        authRepository: UserAuthRepository,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): SignInUseCase {
        return SignInUseCaseImpl(
            authRepository = authRepository,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }

    @Provides
    @Singleton
    fun provideValidateSignUpUseCase (): ValidateSignUpUseCase {
        return ValidateSignUpUseCaseImpl()
    }

    @Provides
    @Singleton
    fun provideValidateSignInUseCase (): ValidateSignInUseCase {
        return ValidateSignInUseCaseImpl()
    }

    @Provides
    fun provideGetAuthSessionUseCase(
        authRepository: UserAuthRepository
    ): GetAuthSessionUseCase {
        return GetAuthSessionUseCaseImpl(authRepository = authRepository)
    }

    @Provides
    fun provideSaveAuthSessionUseCase(
        authRepository: UserAuthRepository,
        coroutineDispatcherProvider: CoroutineDispatcherProvider
    ): SaveAuthSessionUseCase
    {
        return SaveAuthSessionUseCaseImpl(
            authRepository = authRepository,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }

    @Provides
    fun provideRemoveAuthSessionUseCase(
        authRepository: UserAuthRepository,
    ): RemoveAuthSessionUseCase
    {
        return RemoveAuthSessionUseCaseImpl(
            authRepository = authRepository,
        )
    }
}