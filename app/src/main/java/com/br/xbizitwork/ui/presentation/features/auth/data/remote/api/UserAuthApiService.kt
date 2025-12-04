package com.br.xbizitwork.ui.presentation.features.auth.data.remote.api

import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignInRequest
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignUpRequest
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.response.SignInResponse
import com.br.xbizitwork.core.model.api.ApiResultResponse

interface UserAuthApiService {
    suspend fun signIn(signInRequest: SignInRequest): SignInResponse
    suspend fun signUp(signUpRequest: SignUpRequest): ApiResultResponse
}