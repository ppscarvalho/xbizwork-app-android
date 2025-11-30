package com.br.xbizitwork.core.data.remote.common.response

import com.google.gson.annotations.SerializedName

data class ApiResultResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String
)
