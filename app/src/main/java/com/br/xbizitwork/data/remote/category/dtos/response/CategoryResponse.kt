package com.br.xbizitwork.data.remote.category.dtos.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para categoria de profissão
 */
data class CategoryResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String
)