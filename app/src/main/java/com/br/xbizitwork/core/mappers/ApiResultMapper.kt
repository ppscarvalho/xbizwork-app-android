package com.br.xbizitwork.core.mappers

import com.br.xbizitwork.data.remote.auth.dtos.responses.SignUpResponseModel
import com.br.xbizitwork.core.model.api.ApiResultResponse

fun ApiResultResponse.toApplicationResultModel() : SignUpResponseModel{
    return SignUpResponseModel(isSuccessful, message)
}