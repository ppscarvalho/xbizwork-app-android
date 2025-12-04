package com.br.xbizitwork.ui.presentation.features.auth.data.remote.request

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)