package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.auth.ChangePasswordModel
import com.br.xbizitwork.domain.model.auth.SignInModel
import com.br.xbizitwork.domain.model.auth.SignUpModel
import com.br.xbizitwork.domain.result.auth.SignInResult
import com.br.xbizitwork.domain.result.auth.SignUpResult
import com.br.xbizitwork.domain.session.AuthSession
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {
    suspend fun signIn(signInModel: SignInModel): DefaultResult<SignInResult>
    suspend fun signUp(signUpModel: SignUpModel): DefaultResult<SignUpResult>
    suspend fun changePassword(changePasswordModel: ChangePasswordModel): DefaultResult<ApiResultModel>

    fun observeSession() : Flow<AuthSession>
    suspend fun saveSession(id: Int, name: String, email: String, token: String)
    suspend fun getSession(): AuthSession?
    suspend fun clearSession()
}