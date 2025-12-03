package com.br.xbizitwork.ui.presentation.features.auth.domain.model

data class SignInResponseModel(
    val name: String? = null,
    val email: String?=null,
    val token: String? = null,
    val isSuccessful: Boolean,
    val message: String? = null,
)
