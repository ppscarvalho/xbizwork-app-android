package com.br.xbizitwork.data.remote.auth.datasource.interfaces

import com.br.xbizitwork.application.request.SignInRequestModel
import com.br.xbizitwork.application.response.ApplicationResponseModel
import com.br.xbizitwork.application.response.ApplicationResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.application.request.SignUpRequestModel

interface UserAuthRemoteDataSource {
    suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<ApplicationResponseModel>
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApplicationResultModel>
}
