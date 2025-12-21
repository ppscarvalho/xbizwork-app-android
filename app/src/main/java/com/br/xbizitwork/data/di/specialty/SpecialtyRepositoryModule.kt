package com.br.xbizitwork.data.di.specialty

import com.br.xbizitwork.data.repository.SpecialtyRepositoryImpl
import com.br.xbizitwork.domain.repository.SpecialtyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SpecialtyRepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindSpecialtyRepository(
        impl: SpecialtyRepositoryImpl
    ): SpecialtyRepository
}
