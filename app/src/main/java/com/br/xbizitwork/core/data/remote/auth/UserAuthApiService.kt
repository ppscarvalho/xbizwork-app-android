package com.br.xbizitwork.core.data.remote.auth

import com.br.xbizitwork.core.data.remote.auth.request.SignInRequest
import com.br.xbizitwork.core.data.remote.auth.request.SignUpRequest
import com.br.xbizitwork.core.data.remote.auth.response.TokenResponse
import com.br.xbizitwork.core.data.remote.common.response.ApiResultResponse

interface UserAuthApiService {
    suspend fun signIn(signInRequest: SignInRequest): TokenResponse
    suspend fun signUp(signUpRequest: SignUpRequest): ApiResultResponse
}