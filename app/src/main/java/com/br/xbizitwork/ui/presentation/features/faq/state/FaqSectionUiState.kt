package com.br.xbizitwork.ui.presentation.features.faq.state

/**
 * UI state for a FAQ section
 * Includes expanded state for accordion behavior
 */
data class FaqSectionUiState(
    val id: Int,
    val title: String,
    val description: String,
    val order: Int,
    val questions: List<FaqQuestionUiState>,
    val isExpanded: Boolean = false
)
