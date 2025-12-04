package com.br.xbizitwork.ui.presentation.features.auth.data.local.datastore

import com.br.xbizitwork.ui.presentation.features.auth.data.local.model.AuthSession
import kotlinx.coroutines.flow.Flow

interface AuthSessionLocalDataSource {
    fun observeSession() : Flow<AuthSession>
    suspend fun saveSession(name: String, email: String, token: String)
    suspend fun getSession(): AuthSession?
    suspend fun clearSession()
}