package com.br.xbizitwork.domain.usecase.auth.sign

import com.br.xbizitwork.core.util.extensions.containsNumber
import com.br.xbizitwork.core.util.extensions.containsSpecialChar
import com.br.xbizitwork.core.util.extensions.containsUpperCase
import com.br.xbizitwork.domain.model.auth.SignInResultValidation

interface ValidateSignInUseCase{
    operator fun invoke(
        email: String,
        password: String,
    ) : SignInResultValidation
}

class ValidateSignInUseCaseImpl : ValidateSignInUseCase{
    override fun invoke(
        email: String,
        password: String,
    ): SignInResultValidation {
        if (email.isEmpty() || password.isEmpty()) {
            return SignInResultValidation.EmptyField
        }
        if ("@" !in email) {
            return SignInResultValidation.NoEmail
        }
        if (password.count() < 8) {
            return SignInResultValidation.PasswordTooShort
        }
        if (!password.containsNumber()) {
            return SignInResultValidation.PasswordNumberMissing
        }
        if (!password.containsUpperCase()) {
            return SignInResultValidation.PasswordUpperCaseMissing
        }
        if (!password.containsSpecialChar()) {
            return SignInResultValidation.PasswordSpecialCharMissing
        }
        return SignInResultValidation.Valid
    }
}