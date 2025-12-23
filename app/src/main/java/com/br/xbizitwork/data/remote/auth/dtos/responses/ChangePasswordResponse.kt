package com.br.xbizitwork.data.remote.auth.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO para desserialização da resposta da API do servidor
 * 
 * Estrutura esperada:
 * {
 *   "isSuccessful": true,
 *   "message": "Senha alterada com sucesso"
 * }
 * 
 * Contém @SerializedName para mapear JSON da API → Kotlin
 * Usado apenas na camada Remote (UserAuthRemoteDataSourceImpl)
 */
data class ChangePasswordResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,

    @SerializedName("message")
    val message: String
)
