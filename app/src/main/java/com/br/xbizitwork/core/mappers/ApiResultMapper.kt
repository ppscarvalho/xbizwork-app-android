package com.br.xbizitwork.core.mappers

import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.core.model.api.ApiResultModel

fun ApiResultResponse.toModel() : ApiResultModel{
    return ApiResultModel(isSuccessful, message)
}