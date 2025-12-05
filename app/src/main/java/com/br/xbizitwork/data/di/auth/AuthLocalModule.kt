package com.br.xbizitwork.data.di.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.br.xbizitwork.data.local.auth.datastore.interfaces.AuthSessionLocalDataSource
import com.br.xbizitwork.data.local.auth.datastore.implementations.AuthSessionLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthLocalModule {
    @Provides
    @Singleton
    fun providerAuthSessionLocal(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile("recipes_prefs")
        }
    )

    @Provides
    @Singleton
    fun provideAuthSessionLocalDataSource(
        dataStorePreferences: DataStore<Preferences>
    ): AuthSessionLocalDataSource {
        return AuthSessionLocalDataSourceImpl(dataStorePreferences)
    }
}