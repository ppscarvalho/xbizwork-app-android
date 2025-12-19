package com.br.xbizitwork.data.remote.subcategory.dtos.response

import com.google.gson.annotations.SerializedName

/**
 * DTO para subcategoria de profissão
 */
data class SubcategoryResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("category_id")
    val categoryId: Int
)
