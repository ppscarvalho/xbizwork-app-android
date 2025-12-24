package com.br.xbizitwork.data.remote.auth.dtos.requests

/**
 * Model interno do aplicativo para alteração de senha
 * Sem @SerializedName - não serializa com JSON
 * Usado em Use Cases, Repositories e ViewModels
 */
data class ChangePasswordRequestModel(
    val currentPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
