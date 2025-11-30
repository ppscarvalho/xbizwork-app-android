package com.br.xbizitwork.ui.presentation.features.auth.domain.model

enum class SignUpResultValidation {
    EmptyField,
    NoEmail,
    PasswordsDoNotMatch,
    PasswordUpperCaseMissing,
    PasswordNumberMissing,
    PasswordSpecialCharMissing,
    PasswordTooShort,
    Valid
}