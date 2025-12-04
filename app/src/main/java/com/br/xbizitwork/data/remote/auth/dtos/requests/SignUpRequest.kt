package com.br.xbizitwork.data.remote.auth.dtos.requests

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)