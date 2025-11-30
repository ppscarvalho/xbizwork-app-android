package com.br.xbizitwork.features.auth.domain.model

import com.google.gson.annotations.SerializedName

data class SignUpRequestModel(
    val name: String,
    val email: String,
    val password: String
)
