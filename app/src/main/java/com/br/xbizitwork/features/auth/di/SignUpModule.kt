package com.br.xbizitwork.features.auth.di

import com.br.xbizitwork.core.data.remote.auth.UserAuthApiService
import com.br.xbizitwork.core.util.common.CoroutineDispatcherProvider
import com.br.xbizitwork.features.auth.data.repository.UserAuthRepositoryImpl
import com.br.xbizitwork.features.auth.data.source.UserAuthRemoteDataSourceImpl
import com.br.xbizitwork.features.auth.domain.repository.UserAuthRepository
import com.br.xbizitwork.features.auth.domain.source.UserAuthRemoteDataSource
import com.br.xbizitwork.features.auth.domain.usecase.SignUpUseCase
import com.br.xbizitwork.features.auth.domain.usecase.SignUpUseCaseImpl
import com.br.xbizitwork.features.auth.domain.usecase.ValidateSignUpUseCase
import com.br.xbizitwork.features.auth.domain.usecase.ValidateSignUpUseCaseImpl
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
        remoteDataSource: UserAuthRemoteDataSource
    ): UserAuthRepository{
        return UserAuthRepositoryImpl(remoteDataSource = remoteDataSource)
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
    fun provideValidateSignUpUseCase (): ValidateSignUpUseCase{
        return ValidateSignUpUseCaseImpl()
    }
}