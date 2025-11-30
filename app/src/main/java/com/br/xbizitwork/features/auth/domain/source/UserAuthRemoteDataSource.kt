package com.br.xbizitwork.features.auth.domain.source

import com.br.xbizitwork.core.data.remote.auth.response.TokenResponse
import com.br.xbizitwork.core.domain.model.ApiResultModel
import com.br.xbizitwork.core.util.common.DefaultResult
import com.br.xbizitwork.features.auth.domain.model.SignInRequestModel
import com.br.xbizitwork.features.auth.domain.model.SignUpRequestModel

interface UserAuthRemoteDataSource {
    suspend fun signIn(signInRequestModel: SignInRequestModel): TokenResponse
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApiResultModel>
}