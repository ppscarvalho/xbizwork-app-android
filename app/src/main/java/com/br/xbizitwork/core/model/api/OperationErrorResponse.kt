package com.br.xbizitwork.core.model.api

import com.google.gson.annotations.SerializedName

data class OperationErrorResponse(
    @SerializedName("httpCode")
    val httpCode: Int,
    @SerializedName("httpCode")
    val message: String,
)