package com.br.xbizitwork.features.auth.data.mappers

import com.br.xbizitwork.core.data.remote.auth.request.SignInRequest
import com.br.xbizitwork.core.data.remote.auth.request.SignUpRequest
import com.br.xbizitwork.features.auth.domain.model.SignInRequestModel
import com.br.xbizitwork.features.auth.domain.model.SignUpRequestModel

fun SignInRequestModel.toSignInRequest(): SignInRequest{
    return SignInRequest(email, password)
}

fun SignUpRequestModel.toSignUpRequest(): SignUpRequest{
    return SignUpRequest(name, email, password)
}