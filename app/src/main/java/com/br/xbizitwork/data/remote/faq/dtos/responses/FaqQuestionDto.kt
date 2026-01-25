package com.br.xbizitwork.data.remote.faq.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO para resposta de uma pergunta de FAQ da API
 * Seguindo o padrão do projeto com @SerializedName
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
