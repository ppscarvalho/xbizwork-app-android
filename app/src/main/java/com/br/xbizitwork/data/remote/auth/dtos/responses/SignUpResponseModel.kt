package com.br.xbizitwork.data.remote.auth.dtos.responses

/**
 * Model interno do aplicativo para resposta de cadastro
 * Sem @SerializedName - não deserializa JSON diretamente
 * Recebe dados mapeados de ApiResultResponse via ApiResultMapper
 * Usado em Repositories, Use Cases e Domain Models
 */
data class SignUpResponseModel(
    val isSuccessful: Boolean,
    val message: String
)
