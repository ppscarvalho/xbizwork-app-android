package com.br.xbizitwork.domain.result.auth

/**
 * Resultado de domínio para operação de alteração de senha
 */
data class ChangePasswordResult(
    val isSuccessful: Boolean,
    val message: String
)
