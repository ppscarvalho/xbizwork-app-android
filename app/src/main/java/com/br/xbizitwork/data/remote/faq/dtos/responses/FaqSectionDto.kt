package com.br.xbizitwork.data.remote.faq.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO for FAQ section from API
 */
data class FaqSectionDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("order")
    val order: Int,

    @SerializedName("questions")
    val questions: List<FaqQuestionDto>
)
