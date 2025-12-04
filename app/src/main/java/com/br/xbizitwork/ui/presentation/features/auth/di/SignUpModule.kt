package com.br.xbizitwork.ui.presentation.features.auth.di

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.ui.presentation.features.auth.data.local.datastore.AuthSessionLocalDataSource
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.api.UserAuthApiService
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.source.UserAuthRemoteDataSource
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.source.UserAuthRemoteDataSourceImpl
import com.br.xbizitwork.ui.presentation.features.auth.data.repository.UserAuthRepositoryImpl
import com.br.xbizitwork.ui.presentation.features.auth.data.usecase.SignInUseCaseImpl
import com.br.xbizitwork.ui.presentation.features.auth.data.usecase.SignUpUseCaseImpl
import com.br.xbizitwork.ui.presentation.features.auth.domain.repository.UserAuthRepository
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.SignInUseCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.SignUpUseCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.ValidateSignInUseCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.ValidateSignInUseCaseImpl
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.ValidateSignUpUseCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.ValidateSignUpUseCaseImpl
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
    ) : UserAuthRemoteDataSource{
        return UserAuthRemoteDataSourceImpl(authApiService = authApiService)
    }

    @Provides
    @Singleton
    fun provideUserAuthRepository(
        remoteDataSource: UserAuthRemoteDataSource,
        localDataSource: AuthSessionLocalDataSource
    ): UserAuthRepository{
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
    ): SignUpUseCase{
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
    ): SignInUseCase{
        return SignInUseCaseImpl(
            authRepository = authRepository,
            coroutineDispatcherProvider = coroutineDispatcherProvider
        )
    }

    @Provides
    @Singleton
    fun provideValidateSignUpUseCase (): ValidateSignUpUseCase{
        return ValidateSignUpUseCaseImpl()
    }

    @Provides
    @Singleton
    fun provideValidateSignInUseCase (): ValidateSignInUseCase{
        return ValidateSignInUseCaseImpl()
    }
}