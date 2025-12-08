package com.br.xbizitwork.data.remote.auth.dtos.requests

/**
 * Model interno do aplicativo para login
 * Sem @SerializedName - não serializa com JSON
 * Usado em Use Cases, Repositories e ViewModels
 */
data class SignInRequestModel(
    val email: String,
    val password: String
)
