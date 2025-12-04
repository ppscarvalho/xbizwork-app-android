package com.br.xbizitwork.ui.presentation.features.auth.data.remote.source

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignInRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.response.SignInResponseModel
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignUpRequestModel

interface UserAuthRemoteDataSource {
    suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<SignInResponseModel>
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApiResultModel>
}