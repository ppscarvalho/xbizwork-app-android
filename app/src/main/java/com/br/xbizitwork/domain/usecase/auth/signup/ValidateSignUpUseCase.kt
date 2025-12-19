package com.br.xbizitwork.domain.usecase.auth.signup

import com.br.xbizitwork.core.util.extensions.containsNumber
import com.br.xbizitwork.core.util.extensions.containsSpecialChar
import com.br.xbizitwork.core.util.extensions.containsUpperCase
import com.br.xbizitwork.domain.validations.auth.SignUpValidationError

interface ValidateSignUpUseCase{
    operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) : SignUpValidationError
}

class ValidateSignUpUseCaseImpl : ValidateSignUpUseCase{
    override fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): SignUpValidationError {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return SignUpValidationError.EmptyField
        }
        if ("@" !in email) {
            return SignUpValidationError.NoEmail
        }
        if (password != confirmPassword) {
            return SignUpValidationError.PasswordsDoNotMatch
        }
        if (password.count() < 8) {
            return SignUpValidationError.PasswordTooShort
        }
        if (!password.containsNumber()) {
            return SignUpValidationError.PasswordNumberMissing
        }
        if (!password.containsUpperCase()) {
            return SignUpValidationError.PasswordUpperCaseMissing
        }
        if (!password.containsSpecialChar()) {
            return SignUpValidationError.PasswordSpecialCharMissing
        }
        return SignUpValidationError.Valid
    }
}