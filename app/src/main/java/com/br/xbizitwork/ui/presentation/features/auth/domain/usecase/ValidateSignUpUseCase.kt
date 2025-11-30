package com.br.xbizitwork.ui.presentation.features.auth.domain.usecase

import com.br.xbizitwork.core.util.extensions.containsNumber
import com.br.xbizitwork.core.util.extensions.containsSpecialChar
import com.br.xbizitwork.core.util.extensions.containsUpperCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignUpResultValidation

interface ValidateSignUpUseCase{
    operator fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) : SignUpResultValidation
}

class ValidateSignUpUseCaseImpl : ValidateSignUpUseCase{
    override fun invoke(
        name: String,
        email: String,
        password: String,
        confirmPassword: String,
    ): SignUpResultValidation {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return SignUpResultValidation.EmptyField
        }
        if ("@" !in email) {
            return SignUpResultValidation.NoEmail
        }
        if (password != confirmPassword) {
            return SignUpResultValidation.PasswordsDoNotMatch
        }
        if (password.count() < 8) {
            return SignUpResultValidation.PasswordTooShort
        }
        if (!password.containsNumber()) {
            return SignUpResultValidation.PasswordNumberMissing
        }
        if (!password.containsUpperCase()) {
            return SignUpResultValidation.PasswordUpperCaseMissing
        }
        if (!password.containsSpecialChar()) {
            return SignUpResultValidation.PasswordSpecialCharMissing
        }
        return SignUpResultValidation.Valid
    }
}