package com.br.xbizitwork.data.remote.auth.dtos.requests

data class SignUpRequestModel(
    val name: String,
    val email: String,
    val password: String
)