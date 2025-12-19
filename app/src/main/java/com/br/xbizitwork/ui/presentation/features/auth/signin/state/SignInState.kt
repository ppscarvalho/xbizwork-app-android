package com.br.xbizitwork.ui.presentation.features.auth.signin.state

data class SignInState(
    val email: String = "ppscarvalho@gmail.com",
    val password: String = "Plutao@2025",

    val isFormValid: Boolean = false,

    val fieldErrorMessage: String? = null,
    val isLoading: Boolean = false,

    val isSuccess: Boolean = false,
    val signUpErrorMessage: String? = null
)
