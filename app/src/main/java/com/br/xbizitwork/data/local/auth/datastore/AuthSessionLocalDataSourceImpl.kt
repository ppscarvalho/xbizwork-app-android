package com.br.xbizitwork.data.local.auth.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.br.xbizitwork.core.util.logging.logError
import com.br.xbizitwork.domain.session.AuthSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthSessionLocalDataSourceImpl @Inject constructor(
    private val dataStorePreferences: DataStore<Preferences>
) : AuthSessionLocalDataSource {

    private object PreferencesKeys {
        val ID_KEY = intPreferencesKey(name = "id_key")
        val NAME_KEY = stringPreferencesKey(name = "name_key")
        val EMAIL_KEY = stringPreferencesKey(name = "email_key")
        val TOKEN_KEY = stringPreferencesKey(name = "token_key")
    }

    override fun observeSession(): Flow<AuthSession> {
        return dataStorePreferences.data
            .catch { error ->
                error.localizedMessage?.let {
                    logError("AUTH_SESSION_ERROR", it)
                }
                emit(emptyPreferences())
            }
            .map { preferences ->
                val id = preferences[PreferencesKeys.ID_KEY] ?: 0
                val token = preferences[PreferencesKeys.TOKEN_KEY] ?: ""
                val name = preferences[PreferencesKeys.NAME_KEY] ?: ""
                val email = preferences[PreferencesKeys.EMAIL_KEY] ?: ""

                AuthSession(
                    id = id,
                    name = name,
                    email = email,
                    token = token
                )
            }
    }

    override suspend fun saveSession(
        id: Int,
        name: String,
        email: String,
        token: String,
    ) {
        dataStorePreferences.edit { preferences ->
            preferences[PreferencesKeys.ID_KEY] = id
            preferences[PreferencesKeys.NAME_KEY] = name
            preferences[PreferencesKeys.EMAIL_KEY] = email
            preferences[PreferencesKeys.TOKEN_KEY] = token
        }
    }


    override suspend fun getSession(): AuthSession? {
        val preferences = dataStorePreferences.data.first()

        val id = preferences[PreferencesKeys.ID_KEY]
        val name = preferences[PreferencesKeys.NAME_KEY]
        val email = preferences[PreferencesKeys.EMAIL_KEY]
        val token = preferences[PreferencesKeys.TOKEN_KEY]

        if (token.isNullOrEmpty() || name.isNullOrEmpty() || email.isNullOrEmpty()) {
            return null
        }

        return AuthSession(
            id = id ?: 0,
            name = name,
            email = email,
            token = token
        )
    }

    override suspend fun clearSession() {
        dataStorePreferences.edit { preferences ->
            preferences.clear()
        }
    }
}