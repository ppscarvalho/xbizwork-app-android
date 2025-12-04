package com.br.xbizitwork.data.remote.auth.dtos.responses

data class SignInResponseModel(
    val name: String? = null,
    val email: String?=null,
    val token: String? = null,
    val isSuccessful: Boolean,
    val message: String? = null,
)