package com.br.xbizitwork.data.di.cep

import com.br.xbizitwork.domain.repository.CepRepository
import com.br.xbizitwork.domain.usecase.cep.GetCepUseCase
import com.br.xbizitwork.domain.usecase.cep.GetCepUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CepUseCaseModule {

    @Provides
    @Singleton
    fun provideGetCepUseCase(
        repository: CepRepository
    ): GetCepUseCase {
        return GetCepUseCaseImpl(repository)
    }
}
