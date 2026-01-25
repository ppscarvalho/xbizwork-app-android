package com.br.xbizitwork.ui.presentation.features.faq.state

/**
 * Estado principal da tela de FAQ
 * Segue o padrão do SkillUiState
 */
data class FaqUiState(
    val sections: List<FaqSectionUiState> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
