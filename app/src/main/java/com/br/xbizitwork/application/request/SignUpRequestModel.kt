package com.br.xbizitwork.application.request

data class SignUpRequestModel(
    val name: String,
    val email: String,
    val password: String
)