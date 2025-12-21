package com.br.xbizitwork.data.remote.specialty.dtos.response

import com.google.gson.annotations.SerializedName

/**
 * Modelo de resposta da API de especialidades
 * Segue o padrão ApiResponse<T>
 */
data class SpecialtyResponseModel(
    @SerializedName("data")
    val data: List<SpecialtyResponse>,

    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,

    @SerializedName("message")
    val message: String
)
