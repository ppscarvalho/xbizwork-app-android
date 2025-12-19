package com.br.xbizitwork.data.remote.auth.dtos.requests

/**
 * Model interno do aplicativo para cadastro
 * Sem @SerializedName - não serializa com JSON
 * Usado em Use Cases, Repositories e ViewModels
 */
data class SignUpRequestModel(
    val name: String,
    val email: String,
    val password: String
)
