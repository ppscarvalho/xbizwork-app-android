package com.br.xbizitwork.data.remote.faq.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO para questão do FAQ
 * Representa a estrutura retornada pela API no endpoint público
 */
data class FaqQuestionResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("question")
    val question: String,
    
    @SerializedName("answer")
    val answer: String,
    
    @SerializedName("order")
    val order: Int
)
