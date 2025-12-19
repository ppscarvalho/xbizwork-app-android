package com.br.xbizitwork.domain.usecase.auth.signin

import com.br.xbizitwork.core.util.extensions.containsNumber
import com.br.xbizitwork.core.util.extensions.containsSpecialChar
import com.br.xbizitwork.core.util.extensions.containsUpperCase
import com.br.xbizitwork.domain.validations.auth.SignInValidationError

interface ValidateSignInUseCase{
    operator fun invoke(
        email: String,
        password: String,
    ) : SignInValidationError
}

class ValidateSignInUseCaseImpl : ValidateSignInUseCase{
    override fun invoke(
        email: String,
        password: String,
    ): SignInValidationError {
        if (email.isEmpty() || password.isEmpty()) {
            return SignInValidationError.EmptyField
        }
        if ("@" !in email) {
            return SignInValidationError.NoEmail
        }
        if (password.count() < 8) {
            return SignInValidationError.PasswordTooShort
        }
        if (!password.containsNumber()) {
            return SignInValidationError.PasswordNumberMissing
        }
        if (!password.containsUpperCase()) {
            return SignInValidationError.PasswordUpperCaseMissing
        }
        if (!password.containsSpecialChar()) {
            return SignInValidationError.PasswordSpecialCharMissing
        }
        return SignInValidationError.Valid
    }
}