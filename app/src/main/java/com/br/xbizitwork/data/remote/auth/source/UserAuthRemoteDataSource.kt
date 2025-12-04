package com.br.xbizitwork.data.remote.auth.source

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel

interface UserAuthRemoteDataSource {
    suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<SignInResponseModel>
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApiResultModel>
}