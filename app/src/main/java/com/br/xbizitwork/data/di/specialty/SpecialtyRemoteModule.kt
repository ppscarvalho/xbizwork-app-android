package com.br.xbizitwork.data.di.specialty

import com.br.xbizitwork.data.remote.specialty.api.SpecialtyApiService
import com.br.xbizitwork.data.remote.specialty.datasource.SpecialtyRemoteDataSource
import com.br.xbizitwork.data.remote.specialty.datasource.SpecialtyRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SpecialtyRemoteModule {

    @Provides
    @Singleton
    fun provideSpecialtyRemoteDataSource(
        specialtyApiService: SpecialtyApiService
    ): SpecialtyRemoteDataSource {
        return SpecialtyRemoteDataSourceImpl(specialtyApiService)
    }
}
