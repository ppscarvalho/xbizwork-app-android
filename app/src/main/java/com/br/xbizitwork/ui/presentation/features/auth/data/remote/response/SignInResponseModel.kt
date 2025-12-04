package com.br.xbizitwork.ui.presentation.features.auth.data.remote.response

data class SignInResponseModel(
    val name: String? = null,
    val email: String?=null,
    val token: String? = null,
    val isSuccessful: Boolean,
    val message: String? = null,
)