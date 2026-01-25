package com.br.xbizitwork.ui.presentation.features.faq.state

/**
 * Main UI state for FAQ screen
 * Following the same pattern as SkillUiState
 */
data class FaqUiState(
    val sections: List<FaqSectionUiState> = emptyList(),
    
    // UI states
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
