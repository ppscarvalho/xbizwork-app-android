package com.br.xbizitwork.data.mappers

import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.auth.dtos.requests.ChangePasswordRequest
import com.br.xbizitwork.data.remote.auth.dtos.requests.ChangePasswordRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequest
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequest
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponse
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignUpResponseModel
import com.br.xbizitwork.domain.model.auth.ChangePasswordModel
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

fun ChangePasswordModel.toChangePasswordRequestModel(): ChangePasswordRequestModel {
    return ChangePasswordRequestModel(
        currentPassword = currentPassword,
        newPassword = newPassword,
        confirmPassword = confirmPassword
    )
}

fun SignInResponseModel.toDomainResponse(): SignInResult {
    logInfo("DOMAIN_MAPPER_DEBUG", "SignInResponseModel mapeado: id=$id, name=$name, email=$email, token=$token")
    return SignInResult(
        id = this.id ?: 0,
        name = this.name ?: "",
        email = this.email ?: "",
        token = this.token ?: "",
        isSuccessful = this.isSuccessful,
        message = this.message
    ).also {
        logInfo("DOMAIN_MAPPER_DEBUG", "SignInResult criado: id=${it.id}, name=${it.name}, email=${it.email}, token=${it.token}")
    }
}

/**
 * Mapeia SignInResponse (compatível com versão atual) para SignInResponseModel
 * Extrai os dados do objeto "data" da resposta
 */
fun SignInResponse.toLoginResponseModel(): SignInResponseModel {
    logInfo("MAPPER_DEBUG", "SignInResponse recebido: data.id=${data.id}, data.name=${data.name}, data.email=${data.email}, data.token=${data.token}, isSuccessful=$isSuccessful")
    return SignInResponseModel(
        id = data.id,
        name = data.name,
        email = data.email,
        token = data.token,
        isSuccessful = isSuccessful,
        message = message
    ).also {
        logInfo("MAPPER_DEBUG", "SignInResponseModel criado: id=${it.id}, name=${it.name}, email=${it.email}, token=${it.token}")
    }
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

fun ChangePasswordRequestModel.toChangePasswordRequest(): ChangePasswordRequest {
    return ChangePasswordRequest(
        currentPassword = currentPassword,
        newPassword = newPassword,
        confirmPassword = confirmPassword
    )
}

fun SignUpResponseModel.toDomainResult(): SignUpResult {
    return SignUpResult(
        isSuccessful = this.isSuccessful,
        message = this.message
    )
}
