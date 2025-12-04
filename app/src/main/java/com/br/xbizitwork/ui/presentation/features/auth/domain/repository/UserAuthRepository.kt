package com.br.xbizitwork.ui.presentation.features.auth.domain.repository

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.ui.presentation.features.auth.data.local.model.AuthSession
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignInRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.response.SignInResponseModel
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignUpRequestModel
import kotlinx.coroutines.flow.Flow

interface UserAuthRepository {
    suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<SignInResponseModel>
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApiResultModel>
    fun observeSession() : Flow<AuthSession>
    suspend fun saveSession(name: String, email: String, token: String)
    suspend fun getSession(): AuthSession?
    suspend fun clearSession()
}
