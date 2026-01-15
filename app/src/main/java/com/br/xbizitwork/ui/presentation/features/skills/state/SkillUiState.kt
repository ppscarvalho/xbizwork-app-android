package com.br.xbizitwork.ui.presentation.features.skills.state

import com.br.xbizitwork.domain.result.category.CategoryResult

data class SkillUiState(
    val categories: List<CategoryResult> = emptyList(),
    val savedSkillIds: List<Int> = emptyList(), // IDs das habilidades já salvas do usuário

    // Estados da UI
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
)
