package com.br.xbizitwork.domain.model.faq

/**
 * Modelo de domínio para uma pergunta/resposta de FAQ
 * Representa uma única questão dentro de uma seção
 */
data class FaqQuestion(
    val id: Int,
    val question: String,
    val answer: String,
    val order: Int
)
