package com.br.xbizitwork.domain.model.auth

/**
 * Modelo de domínio para alteração de senha
 * Usado na comunicação entre UseCase e Repository
 */
data class ChangePasswordModel(
    val currentPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
