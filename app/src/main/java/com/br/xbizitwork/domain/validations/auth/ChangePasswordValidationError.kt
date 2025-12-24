package com.br.xbizitwork.domain.validations.auth

enum class ChangePasswordValidationError {
    EmptyField,
    PasswordTooShort,
    PasswordsDoNotMatch,
    PasswordUpperCaseMissing,
    PasswordNumberMissing,
    PasswordSpecialCharMissing,
    Valid
}
