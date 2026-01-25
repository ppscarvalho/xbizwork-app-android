package com.br.xbizitwork.domain.model.faq

/**
 * Modelo de domínio para seção do FAQ
 */
data class FaqSectionModel(
    val id: Int,
    val title: String,
    val description: String,
    val order: Int,
    val questions: List<FaqQuestionModel>
)
