package com.br.xbizitwork.ui.presentation.features.faq.state

/**
 * Estado de UI para questão do FAQ
 * Inclui estado de expansão para accordion
 */
data class FaqQuestionUiState(
    val id: Int,
    val question: String,
    val answer: String,
    val order: Int,
    val isExpanded: Boolean = false
)
