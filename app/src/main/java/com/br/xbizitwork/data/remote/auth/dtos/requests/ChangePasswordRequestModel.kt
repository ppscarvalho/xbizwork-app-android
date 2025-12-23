package com.br.xbizitwork.data.remote.auth.dtos.requests

/**
 * Modelo intermediário usado internamente na aplicação
 * Não contém anotações de serialização
 * Mapeado para ChangePasswordRequest antes de enviar à API
 */
data class ChangePasswordRequestModel(
    val currentPassword: String,
    val newPassword: String
)
