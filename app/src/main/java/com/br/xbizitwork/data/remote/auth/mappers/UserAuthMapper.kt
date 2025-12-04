package com.br.xbizitwork.data.remote.auth.mappers

import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequest
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequest
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponse
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequestModel

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