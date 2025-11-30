package com.br.xbizitwork.core.domain.mappers

import com.br.xbizitwork.core.data.remote.common.response.ApiResultResponse
import com.br.xbizitwork.core.domain.model.ApiResultModel

fun ApiResultResponse.toModel() : ApiResultModel{
    return ApiResultModel(isSuccessful, message)
}