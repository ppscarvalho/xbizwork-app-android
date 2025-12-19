package com.br.xbizitwork.data.remote.auth.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO para desserialização da resposta da API do servidor
 * 
 * Estrutura esperada:
 * {
 *   "data": {
 *     "id": 14,
 *     "name": "...",
 *     "email": "...",
 *     "token": "..."
 *   },
 *   "isSuccessful": true,
 *   "message": "..."
 * }
 * 
 * Contém @SerializedName para mapear JSON da API → Kotlin
 * Usado apenas na camada Remote (UserAuthRemoteDataSourceImpl)
 * Mapeado para SignInResponseModel para uso interno da aplicação
 */
data class SignInResponse(
    @SerializedName("data")
    val data: UserData,

    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,

    @SerializedName("message")
    val message: String
)
