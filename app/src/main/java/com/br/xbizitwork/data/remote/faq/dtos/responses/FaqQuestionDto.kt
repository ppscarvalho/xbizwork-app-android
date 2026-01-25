package com.br.xbizitwork.data.remote.faq.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO for FAQ question from API
 */
data class FaqQuestionDto(
    @SerializedName("id")
    val id: Int,

    @SerializedName("question")
    val question: String,

    @SerializedName("answer")
    val answer: String,

    @SerializedName("order")
    val order: Int
)
