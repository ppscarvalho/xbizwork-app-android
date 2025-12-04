package com.br.xbizitwork.ui.presentation.features.auth.signup.state

data class SignUpState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",

    val isFormValid: Boolean = false,

    val fieldErrorMessage: String? = null,
    val isLoading: Boolean = false,

    val isSuccess: Boolean = false,
    val signUpErrorMessage: String? = null
)