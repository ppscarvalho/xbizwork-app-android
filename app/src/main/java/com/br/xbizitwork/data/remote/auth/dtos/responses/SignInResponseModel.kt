package com.br.xbizitwork.data.remote.auth.dtos.responses

/**
 * Model interno do aplicativo para resposta de login
 * Sem @SerializedName - não deserializa JSON diretamente
 * Recebe dados mapeados de SignInResponse via AuthMappers
 * Usado em Repositories, Use Cases e Domain Models
 */
data class SignInResponseModel(
    val name: String? = null,
    val email: String?=null,
    val token: String? = null,
    val isSuccessful: Boolean,
    val message: String? = null,
)