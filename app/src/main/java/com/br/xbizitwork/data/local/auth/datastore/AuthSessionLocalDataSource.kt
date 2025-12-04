package com.br.xbizitwork.data.local.auth.datastore

import com.br.xbizitwork.data.local.auth.model.AuthSession
import kotlinx.coroutines.flow.Flow

interface AuthSessionLocalDataSource {
    fun observeSession() : Flow<AuthSession>
    suspend fun saveSession(name: String, email: String, token: String)
    suspend fun getSession(): AuthSession?
    suspend fun clearSession()
}