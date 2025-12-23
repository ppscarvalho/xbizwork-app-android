package com.br.xbizitwork.domain.validations.auth

enum class ChangePasswordValidationError {
    EmptyField,
    PasswordTooShort,
    PasswordUpperCaseMissing,
    PasswordNumberMissing,
    PasswordSpecialCharMissing,
    PasswordsDoNotMatch,
    NewPasswordSameAsOld,
    Valid
}
