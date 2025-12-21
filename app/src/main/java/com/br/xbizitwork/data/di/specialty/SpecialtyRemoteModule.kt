package com.br.xbizitwork.data.di.specialty

import com.br.xbizitwork.data.remote.specialty.datasource.SpecialtyRemoteDataSource
import com.br.xbizitwork.data.remote.specialty.datasource.SpecialtyRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SpecialtyRemoteModule {
    
    @Binds
    @Singleton
    abstract fun bindSpecialtyRemoteDataSource(
        impl: SpecialtyRemoteDataSourceImpl
    ): SpecialtyRemoteDataSource
}
