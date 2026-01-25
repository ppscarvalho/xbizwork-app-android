package com.br.xbizitwork.ui.presentation.features.faq.state

/**
 * Estado UI para uma pergunta individual do FAQ
 * Contém estado de expansão para accordion
 */
data class FaqQuestionUiState(
    val id: Int,
    val question: String,
    val answer: String,
    val order: Int,
    val isExpanded: Boolean = false
)
