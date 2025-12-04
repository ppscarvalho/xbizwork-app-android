package com.br.xbizitwork.ui.presentation.features.auth.data.mappers

import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignInRequest
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignUpRequest
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.response.SignInResponse
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignInRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.response.SignInResponseModel
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignUpRequestModel

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