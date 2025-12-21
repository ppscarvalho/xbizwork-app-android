package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.model.auth.SignInModel
import com.br.xbizitwork.domain.model.auth.SignUpModel
import com.br.xbizitwork.domain.result.auth.SignInResult
import com.br.xbizitwork.domain.result.auth.SignUpResult
import com.br.xbizitwork.domain.session.AuthSession
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {
    suspend fun signIn(signInModel: SignInModel): DomainDefaultResult<SignInResult>
    suspend fun signUp(signUpModel: SignUpModel): DomainDefaultResult<SignUpResult>

    fun observeSession() : Flow<AuthSession>
    suspend fun saveSession(id: Int, name: String, email: String, token: String)
    suspend fun getSession(): AuthSession?
    suspend fun clearSession()
}