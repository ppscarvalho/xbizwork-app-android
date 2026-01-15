package com.br.xbizitwork.data.di.user

import com.br.xbizitwork.domain.repository.UserAuthRepository
import com.br.xbizitwork.domain.repository.UserRepository
import com.br.xbizitwork.domain.usecase.user.GetUserByIdUseCase
import com.br.xbizitwork.domain.usecase.user.GetUserByIdUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserUseCaseModule {

    @Provides
    @Singleton
    fun provideGetUserByIdUseCase(
        repository: UserRepository,
    ): GetUserByIdUseCase {
        return GetUserByIdUseCaseImpl(repository)
    }
}