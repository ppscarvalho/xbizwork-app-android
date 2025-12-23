package com.br.xbizitwork.domain.usecase.auth.changepassword

import com.br.xbizitwork.core.util.extensions.containsNumber
import com.br.xbizitwork.core.util.extensions.containsSpecialChar
import com.br.xbizitwork.core.util.extensions.containsUpperCase
import com.br.xbizitwork.domain.validations.auth.ChangePasswordValidationError

/**
 * UseCase responsável por validação de senha
 * 
 * Responsabilidades:
 * - Campos obrigatórios
 * - Confirmação de senha
 * - Política de senha (mínimo 8 caracteres, maiúscula, número, caractere especial)
 * - Nova senha diferente da senha atual
 * 
 * ⚠️ NÃO acessa banco
 * ⚠️ NÃO conhece usuário
 * ⚠️ NÃO conhece sessão
 */
interface ValidatePasswordUseCase {
    operator fun invoke(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ChangePasswordValidationError
}

class ValidatePasswordUseCaseImpl : ValidatePasswordUseCase {
    override fun invoke(
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ): ChangePasswordValidationError {
        // Campos obrigatórios
        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            return ChangePasswordValidationError.EmptyField
        }

        // Confirmação de senha
        if (newPassword != confirmPassword) {
            return ChangePasswordValidationError.PasswordsDoNotMatch
        }

        // Nova senha diferente da senha atual
        if (currentPassword == newPassword) {
            return ChangePasswordValidationError.NewPasswordSameAsOld
        }

        // Política de senha - mínimo 8 caracteres
        if (newPassword.count() < 8) {
            return ChangePasswordValidationError.PasswordTooShort
        }

        // Política de senha - deve conter número
        if (!newPassword.containsNumber()) {
            return ChangePasswordValidationError.PasswordNumberMissing
        }

        // Política de senha - deve conter maiúscula
        if (!newPassword.containsUpperCase()) {
            return ChangePasswordValidationError.PasswordUpperCaseMissing
        }

        // Política de senha - deve conter caractere especial
        if (!newPassword.containsSpecialChar()) {
            return ChangePasswordValidationError.PasswordSpecialCharMissing
        }

        return ChangePasswordValidationError.Valid
    }
}
