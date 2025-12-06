package com.br.xbizitwork.data.remote.auth.datasource.interfaces

import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.ApplicationResponseModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.ApplicationResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequestModel

interface UserAuthRemoteDataSource {
    suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<ApplicationResponseModel>
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApplicationResultModel>
}
