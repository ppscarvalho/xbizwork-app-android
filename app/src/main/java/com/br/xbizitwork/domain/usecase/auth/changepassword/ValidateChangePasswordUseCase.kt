package com.br.xbizitwork.domain.usecase.auth.changepassword

import com.br.xbizitwork.core.util.extensions.containsNumber
import com.br.xbizitwork.core.util.extensions.containsSpecialChar
import com.br.xbizitwork.core.util.extensions.containsUpperCase
import com.br.xbizitwork.domain.validations.auth.ChangePasswordValidationError

/**
 * UseCase para validação dos campos de alteração de senha
 * Validação ocorre APENAS no cliente para melhorar UX
 * Validação de credenciais (senha atual) ocorre APENAS no backend
 */
interface ValidateChangePasswordUseCase {
    operator fun invoke(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ChangePasswordValidationError
}

class ValidateChangePasswordUseCaseImpl : ValidateChangePasswordUseCase {
    override fun invoke(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ChangePasswordValidationError {
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            return ChangePasswordValidationError.EmptyField
        }
        if (newPassword != confirmPassword) {
            return ChangePasswordValidationError.PasswordsDoNotMatch
        }
        if (newPassword.count() < 8) {
            return ChangePasswordValidationError.PasswordTooShort
        }
        if (!newPassword.containsNumber()) {
            return ChangePasswordValidationError.PasswordNumberMissing
        }
        if (!newPassword.containsUpperCase()) {
            return ChangePasswordValidationError.PasswordUpperCaseMissing
        }
        if (!newPassword.containsSpecialChar()) {
            return ChangePasswordValidationError.PasswordSpecialCharMissing
        }
        return ChangePasswordValidationError.Valid
    }
}
