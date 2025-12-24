package com.br.xbizitwork.data.remote.auth.api

import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.data.remote.auth.dtos.requests.ChangePasswordRequest
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequest
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequest
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponse

interface UserAuthApiService {
    suspend fun signIn(signInRequest: SignInRequest): SignInResponse
    suspend fun signUp(signUpRequest: SignUpRequest): ApiResultResponse
    suspend fun changePassword(changePasswordRequest: ChangePasswordRequest): ApiResultResponse
}
