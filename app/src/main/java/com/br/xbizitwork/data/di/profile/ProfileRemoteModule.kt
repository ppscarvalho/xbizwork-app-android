package com.br.xbizitwork.data.di.profile

import com.br.xbizitwork.data.remote.profile.api.ProfileApiService
import com.br.xbizitwork.data.remote.profile.datasource.ProfileRemoteDataSourceImpl
import com.br.xbizitwork.data.remote.profile.datasource.ProfileRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileRemoteModule {

    @Provides
    @Singleton
    fun provideProfileRemoteDataSource(
        profileApiService: ProfileApiService
    ): ProfileRemoteDataSource {
        return ProfileRemoteDataSourceImpl(profileApiService)
    }
}

