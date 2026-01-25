package com.br.xbizitwork.ui.presentation.features.faq.state

/**
 * Estado de UI para seção do FAQ
 * Inclui estado de expansão para accordion
 */
data class FaqSectionUiState(
    val id: Int,
    val title: String,
    val description: String,
    val order: Int,
    val questions: List<FaqQuestionUiState>,
    val isExpanded: Boolean = false
)
