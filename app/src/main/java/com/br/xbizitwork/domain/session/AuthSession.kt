package com.br.xbizitwork.domain.session

data class AuthSession(
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val errorMessage: String? = null
)