package com.br.xbizitwork.core.data.remote.auth

import com.br.xbizitwork.core.data.remote.auth.request.SignInRequest
import com.br.xbizitwork.core.data.remote.auth.request.SignUpRequest
import com.br.xbizitwork.core.data.remote.auth.response.SignInResponse
import com.br.xbizitwork.core.data.remote.common.response.ApiResultResponse

interface UserAuthApiService {
    suspend fun signIn(signInRequest: SignInRequest): SignInResponse
    suspend fun signUp(signUpRequest: SignUpRequest): ApiResultResponse
}