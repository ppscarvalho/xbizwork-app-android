package com.br.xbizitwork.ui.presentation.features.auth.domain.repository

import com.br.xbizitwork.core.domain.model.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignInRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignInResponseModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignUpRequestModel

interface UserAuthRepository {
    suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<SignInResponseModel>
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApiResultModel>
}
