package com.br.xbizitwork.data.remote.specialty.dtos.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para especialidade de uma categoria
 */
data class SpecialtyResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("categoryId")
    val categoryId: Int
)
