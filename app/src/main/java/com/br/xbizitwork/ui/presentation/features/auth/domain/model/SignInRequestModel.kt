package com.br.xbizitwork.ui.presentation.features.auth.domain.model

import com.google.gson.annotations.SerializedName

data class SignInRequestModel(
    val email: String,
    val password: String
)