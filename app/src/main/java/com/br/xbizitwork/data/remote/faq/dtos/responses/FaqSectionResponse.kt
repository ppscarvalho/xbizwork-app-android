package com.br.xbizitwork.data.remote.faq.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO para seção do FAQ
 * Representa a estrutura retornada pela API no endpoint público
 */
data class FaqSectionResponse(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("order")
    val order: Int,
    
    @SerializedName("questions")
    val questions: List<FaqQuestionResponse>
)
