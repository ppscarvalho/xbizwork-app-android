package com.br.xbizitwork.ui.presentation.features.faq.state

/**
 * UI state for a single FAQ question
 * Includes expanded state for accordion behavior
 */
data class FaqQuestionUiState(
    val id: Int,
    val question: String,
    val answer: String,
    val order: Int,
    val isExpanded: Boolean = false
)
