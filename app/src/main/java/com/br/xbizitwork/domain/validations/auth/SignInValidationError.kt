package com.br.xbizitwork.domain.validations.auth

enum class SignInValidationError {
    EmptyField,
    NoEmail,
    PasswordsDoNotMatch,
    PasswordUpperCaseMissing,
    PasswordNumberMissing,
    PasswordSpecialCharMissing,
    PasswordTooShort,
    Valid
}