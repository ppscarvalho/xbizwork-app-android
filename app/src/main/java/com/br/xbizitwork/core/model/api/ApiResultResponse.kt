package com.br.xbizitwork.core.model.api

import com.google.gson.annotations.SerializedName

data class ApiResultResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String
)