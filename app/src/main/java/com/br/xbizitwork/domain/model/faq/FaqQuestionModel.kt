package com.br.xbizitwork.domain.model.faq

/**
 * Modelo de domínio para questão do FAQ
 */
data class FaqQuestionModel(
    val id: Int,
    val question: String,
    val answer: String,
    val order: Int
)
