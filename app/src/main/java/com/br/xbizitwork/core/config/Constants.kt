package com.br.xbizitwork.core.config

object Constants {
    const val TYPE_QRCODE_GENERATED = "TYPE_QRCODE_GENERATED"
    const val TYPE_USERS_CONNECTIONS = "TYPE_USERS_CONNECTIONS"
    const val TYPE_TIME_REMAINING = "TYPE_TIME_REMAINING"
    const val TYPE_QRCODE_EXPIRATION_MESSAGE = "TYPE_QRCODE_EXPIRATION_MESSAGE"
    const val TYPE_ERROR_MESSAGE = "TYPE_ERROR_MESSAGE"
    const val TYPE_AWAITING_CONNECTION = "TYPE_AWAITING_CONNECTION"
    const val TYPE_CONNECTION_STATUS = "TYPE_CONNECTION_STATUS"
    const val TYPE_SEND_QRCODE = "TYPE_SEND_QRCODE"
    const val TYPE_USER_ACTION = "TYPE_USER_ACTION"

    const val TYPE_CANCEL_CONNECTION = "TYPE_CANCEL_CONNECTION"

    object ValidationAuthMessages {
        const val EMPTY_FIELD = "Preencha todos os campos"
        const val INVALID_EMAIL = "Email inválido"
        const val PASSWORD_TOO_SHORT = "Senha muito curta"
        const val PASSWORDS_DO_NOT_MATCH = "As senhas não coincidem"
        const val PASSWORD_UPPERCASE_MISSING = "A senha precisa conter pelo menos um caractere maiúsculo"
        const val PASSWORD_SPECIAL_CHAR_MISSING = "A senha precisa conter pelo menos um caractere especial"
        const val PASSWORD_NUMBER_MISSING = "A senha precisa conter pelo menos um número"
        const val PHONE_NUMBER_INVALID = "O número de telefone precisa conter 11 caracteres"
    }
}