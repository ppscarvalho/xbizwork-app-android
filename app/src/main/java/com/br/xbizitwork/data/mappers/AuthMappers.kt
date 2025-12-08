package com.br.xbizitwork.data.mappers

import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequest
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequest
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponse
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignUpResponseModel
import com.br.xbizitwork.domain.model.auth.SignInModel
import com.br.xbizitwork.domain.model.auth.SignUpModel
import com.br.xbizitwork.domain.result.auth.SignInResult
import com.br.xbizitwork.domain.result.auth.SignUpResult

fun SignInModel.toSignInRequestModel(): SignInRequestModel {
    return SignInRequestModel(
        email = email,
        password = password
    )
}

fun SignUpModel.toSignUpRequestModel(): SignUpRequestModel {
    return SignUpRequestModel(
        email = email,
        name = name,
        password = password
    )
}

fun SignInResponseModel.toDomainResponse(): SignInResult {
    return SignInResult(
        name = this.name ?: "",
        email = this.email ?: "",
        token = this.token ?: "",
        isSuccessful = this.isSuccessful,
        message = this.message
    )
}

/**
 * Mapeia SignInResponse (compatível com versão atual) para SignInResponseModel
 */
fun SignInResponse.toLoginResponseModel(): SignInResponseModel {
    return SignInResponseModel(
        name = name,
        email = email,
        token = token,
        isSuccessful = isSuccessful,
        message = message
    )
}

fun SignInRequestModel.toLoginRequest(): SignInRequest {
    return SignInRequest(
        email = email,
        password = password
    )
}

fun SignUpRequestModel.toSignUpRequest(): SignUpRequest {
    return SignUpRequest(
        name = name,
        email = email,
        password = password
    )
}

fun SignUpResponseModel.toDomainResult(): SignUpResult {
    return SignUpResult(
        isSuccessful = this.isSuccessful,
        message = this.message
    )
}
