package com.br.xbizitwork.data.remote.faq.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO para resposta de uma seção de FAQ da API
 * Seguindo o padrão do projeto com @SerializedName
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
