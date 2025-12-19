package com.br.xbizitwork.domain.model.profile

/**
 * Enum representando os possíveis resultados da validação do perfil
 */
enum class ProfileResultValidation {
    EmptyName,
    NameTooShort,
    InvalidEmail,
    InvalidPhone,
    Valid
}

