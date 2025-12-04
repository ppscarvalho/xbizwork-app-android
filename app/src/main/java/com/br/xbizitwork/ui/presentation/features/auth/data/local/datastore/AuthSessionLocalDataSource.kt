package com.br.xbizitwork.ui.presentation.features.auth.data.local.datastore

import com.br.xbizitwork.ui.presentation.features.auth.data.local.model.AuthSession
import kotlinx.coroutines.flow.Flow

interface AuthSessionLocalDataSource {
    fun observeSession() : Flow<AuthSession>
    suspend fun saveSession(name: String, token: String, email: String)
    suspend fun getSession(): AuthSession?
    suspend fun clearSession()
}