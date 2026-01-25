package com.br.xbizitwork.domain.model.faq

/**
 * Domain model representing a FAQ question
 */
data class FaqQuestion(
    val id: Int,
    val question: String,
    val answer: String,
    val order: Int
)
