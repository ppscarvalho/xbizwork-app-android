package com.br.xbizitwork.data.local.auth.model

data class AuthSession(
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val errorMessage: String? = null
)