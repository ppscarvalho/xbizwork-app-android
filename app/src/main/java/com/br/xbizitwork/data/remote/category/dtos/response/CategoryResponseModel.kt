package com.br.xbizitwork.data.remote.category.dtos.response

import com.google.gson.annotations.SerializedName

/**
 * Model interno do aplicativo para resposta de cadastro
 * Sem @SerializedName - não deserializa JSON diretamente
 * Recebe dados mapeados de ApiResultResponse via ApiResultMapper
 * Usado em Repositories, Use Cases e Domain Models
 */
data class CategoryResponseModel(
    val id: Int,
    val description: String
)