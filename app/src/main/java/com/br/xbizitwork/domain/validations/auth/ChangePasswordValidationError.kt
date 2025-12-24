package com.br.xbizitwork.domain.validations.auth

enum class ChangePasswordValidationError {
    EmptyField,
    PasswordsDoNotMatch,
    PasswordUpperCaseMissing,
    PasswordNumberMissing,
    PasswordSpecialCharMissing,
    PasswordTooShort,
    Valid
}
