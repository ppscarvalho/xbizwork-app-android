package com.br.xbizitwork.domain.model.faq

/**
 * Modelo de domínio para uma seção de FAQ
 * Contém título, descrição e lista de perguntas/respostas
 */
data class FaqSection(
    val id: Int,
    val title: String,
    val description: String,
    val order: Int,
    val questions: List<FaqQuestion>
)
