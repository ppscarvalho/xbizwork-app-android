package com.br.xbizitwork.domain.model.auth

/**
 * Modelo de domínio para alteração de senha
 * Não conhece detalhes de infraestrutura ou implementação
 */
data class ChangePasswordModel(
    val currentPassword: String,
    val newPassword: String
)
