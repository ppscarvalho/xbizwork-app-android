package com.br.xbizitwork.ui.presentation.features.auth.data.local.model

data class AuthSession(
    val name: String = "",
    val email: String = "",
    val token: String = "",
    val errorMessage: String? = null
)
