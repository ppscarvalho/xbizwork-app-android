package com.br.xbizitwork.ui.presentation.features.faq.state

/**
 * Estado principal da tela de FAQ
 * Seguindo o padrão do SkillUiState
 */
data class FaqUiState(
    val sections: List<FaqSectionUiState> = emptyList(),
    
    // Estados da UI
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
)
