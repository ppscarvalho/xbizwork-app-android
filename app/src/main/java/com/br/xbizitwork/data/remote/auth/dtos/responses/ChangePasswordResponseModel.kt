package com.br.xbizitwork.data.remote.auth.dtos.responses

/**
 * Modelo intermediário usado internamente na aplicação
 * Não contém anotações de serialização
 * Mapeado a partir de ChangePasswordResponse recebido da API
 */
data class ChangePasswordResponseModel(
    val isSuccessful: Boolean,
    val message: String
)
