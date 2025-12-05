package com.br.xbizitwork.core.mappers

import com.br.xbizitwork.application.response.ApplicationResultModel
import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.core.model.api.ApiResultModel

fun ApiResultResponse.toApplicationResultModel() : ApplicationResultModel{
    return ApplicationResultModel(isSuccessful, message)
}