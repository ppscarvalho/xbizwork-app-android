package com.br.xbizitwork.domain.repository.auth

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel
import com.br.xbizitwork.data.local.auth.model.AuthSession
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequestModel
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {
    suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<SignInResponseModel>
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApiResultModel>
    fun observeSession() : Flow<AuthSession>
    suspend fun saveSession(name: String, email: String, token: String)
    suspend fun getSession(): AuthSession?
    suspend fun clearSession()
}