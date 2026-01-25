package com.br.xbizitwork.ui.presentation.features.faq.state

/**
 * Estado UI para uma seção do FAQ
 * Contém lista de perguntas e estado de expansão para accordion
 */
data class FaqSectionUiState(
    val id: Int,
    val title: String,
    val description: String,
    val order: Int,
    val questions: List<FaqQuestionUiState>,
    val isExpanded: Boolean = false
)
