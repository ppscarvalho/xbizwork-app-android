package com.br.xbizitwork.ui.presentation.features.auth.domain.model

enum class SignInResultValidation {
    EmptyField,
    NoEmail,
    PasswordsDoNotMatch,
    PasswordUpperCaseMissing,
    PasswordNumberMissing,
    PasswordSpecialCharMissing,
    PasswordTooShort,
    Valid
}