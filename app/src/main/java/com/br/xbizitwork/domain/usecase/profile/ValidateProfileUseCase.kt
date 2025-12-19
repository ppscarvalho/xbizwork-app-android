package com.br.xbizitwork.domain.usecase.profile

import com.br.xbizitwork.domain.model.profile.ProfileResultValidation

/**
 * UseCase para validar dados do perfil antes de enviar para API
 * Seguindo o mesmo padrão do ValidateSignUpUseCase
 */
interface ValidateProfileUseCase {
    operator fun invoke(
        name: String,
        email: String,
        phone: String?
    ): ProfileResultValidation
}

/**
 * Implementação do ValidateProfileUseCase
 * Valida regras de negócio do perfil
 */
class ValidateProfileUseCaseImpl : ValidateProfileUseCase {

    override fun invoke(
        name: String,
        email: String,
        phone: String?
    ): ProfileResultValidation {

        // Validar nome
        if (name.isBlank()) {
            return ProfileResultValidation.EmptyName
        }

        if (name.length < 3) {
            return ProfileResultValidation.NameTooShort
        }

        // Validar email
        if (!email.contains("@")) {
            return ProfileResultValidation.InvalidEmail
        }

        // Validar telefone (se preenchido)
        if (phone != null && phone.isNotBlank() && phone.length != 11) {
            return ProfileResultValidation.InvalidPhone
        }

        return ProfileResultValidation.Valid
    }
}

