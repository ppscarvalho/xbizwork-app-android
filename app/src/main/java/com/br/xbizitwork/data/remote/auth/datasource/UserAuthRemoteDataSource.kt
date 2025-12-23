package com.br.xbizitwork.data.remote.auth.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.auth.dtos.requests.ChangePasswordRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.ChangePasswordResponseModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignUpResponseModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel

interface UserAuthRemoteDataSource {
    suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<SignInResponseModel>
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<SignUpResponseModel>
    suspend fun changePassword(changePasswordRequestModel: ChangePasswordRequestModel): DefaultResult<ChangePasswordResponseModel>
}