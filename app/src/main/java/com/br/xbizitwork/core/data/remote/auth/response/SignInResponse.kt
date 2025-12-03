package com.br.xbizitwork.core.data.remote.auth.response

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,

    @SerializedName("message")
    val message: String
)