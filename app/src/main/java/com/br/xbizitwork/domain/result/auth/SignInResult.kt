package com.br.xbizitwork.domain.result.auth

data class SignInResult(
    val id: Int? = null,
    val name: String? = null,
    val email: String?=null,
    val token: String? = null,
    val isSuccessful: Boolean,
    val message: String? = null,
)