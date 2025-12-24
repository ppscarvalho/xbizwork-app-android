package com.br.xbizitwork.domain.usecase.auth.changepassword

import com.br.xbizitwork.core.util.extensions.containsNumber
import com.br.xbizitwork.core.util.extensions.containsSpecialChar
import com.br.xbizitwork.core.util.extensions.containsUpperCase
import com.br.xbizitwork.domain.validations.auth.ChangePasswordValidationError
import com.br.xbizitwork.domain.validations.auth.SignUpValidationError

/**
 * UseCase para validação de campos de alteração de senha
 * Seguindo o mesmo padrão do ValidateSignUpUseCase
 * 
 * IMPORTANTE: Validação ocorre APENAS no client para UX
 * Backend também valida todos os campos
 */
interface ValidateChangePasswordUseCase {
    operator fun invoke(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ChangePasswordValidationError
}

/**
 * Implementação do ValidateChangePasswordUseCase
 * Valida apenas campos vazios e correspondência entre senhas
 * NÃO valida formato/complexidade de senha (backend valida)
 */
class ValidateChangePasswordUseCaseImpl : ValidateChangePasswordUseCase {
    override fun invoke(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ChangePasswordValidationError {
        // Valida campos vazios
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            return ChangePasswordValidationError.EmptyField
        }
        // Valida se nova senha e confirmação são iguais
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
