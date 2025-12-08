package com.br.xbizitwork.data.remote.auth.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO para desserialização da resposta da API do servidor
 * Contém @SerializedName para mapear JSON da API → Kotlin
 * Usado apenas na camada Remote (UserAuthRemoteDataSourceImpl)
 * Mapeado para SignInResponseModel para uso interno da aplicação
 */
data class SignInResponse(
    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("token")
    val token: String,

    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,

    @SerializedName("message")
    val message: String
)