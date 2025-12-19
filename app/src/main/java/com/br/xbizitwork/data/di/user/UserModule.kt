package com.br.xbizitwork.data.di.user

import com.br.xbizitwork.data.remote.user.api.UserApiService
import com.br.xbizitwork.data.remote.user.api.UserApiServiceImpl
import com.br.xbizitwork.domain.usecase.user.GetUserByIdUseCase
import com.br.xbizitwork.domain.usecase.user.GetUserByIdUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

/**
 * Módulo de Dependency Injection para User
 */
@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    @Singleton
    fun provideUserApiService(
        httpClient: HttpClient
    ): UserApiService {
        return UserApiServiceImpl(httpClient)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface UserBindsModule {

    @Binds
    @Singleton
    fun bindGetUserByIdUseCase(
        impl: GetUserByIdUseCaseImpl
    ): GetUserByIdUseCase
}

