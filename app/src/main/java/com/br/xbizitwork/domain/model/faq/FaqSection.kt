package com.br.xbizitwork.domain.model.faq

/**
 * Domain model representing a FAQ section with its questions
 */
data class FaqSection(
    val id: Int,
    val title: String,
    val description: String,
    val order: Int,
    val questions: List<FaqQuestion>
)
