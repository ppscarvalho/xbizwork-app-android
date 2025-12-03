package com.br.xbizitwork.ui.presentation.features.auth.data.mappers

import com.br.xbizitwork.core.data.remote.auth.request.SignInRequest
import com.br.xbizitwork.core.data.remote.auth.request.SignUpRequest
import com.br.xbizitwork.core.data.remote.auth.response.SignInResponse
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignInRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignInResponseModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignUpRequestModel

fun SignInRequestModel.toSignInRequest(): SignInRequest{
    return SignInRequest(
        email = email,
        password = password
    )
}

fun SignUpRequestModel.toSignUpRequest(): SignUpRequest{
    return SignUpRequest(
        name = name,
        email = email,
        password = password
    )
}

fun SignInResponse.toSignInResponseModel(): SignInResponseModel {
    return SignInResponseModel(
        name = name,
        email = email,
        token = token,
        isSuccessful = isSuccessful,
        message = message
    )
}