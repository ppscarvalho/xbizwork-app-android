package com.br.xbizitwork.data.remote.auth.dtos.requests

import com.google.gson.annotations.SerializedName

/**
 * DTO para serialização com a API do servidor
 * Contém @SerializedName para mapear JSON ↔ Kotlin
 * Usado apenas na camada Remote (UserAuthRemoteDataSourceImpl)
 */
data class ChangePasswordRequest(
    @SerializedName("currentPassword")
    val currentPassword: String,
    @SerializedName("newPassword")
    val newPassword: String
)
