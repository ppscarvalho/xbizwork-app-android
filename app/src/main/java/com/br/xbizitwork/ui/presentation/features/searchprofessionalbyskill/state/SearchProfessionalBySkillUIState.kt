package com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.state

import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill

/**
 * UI State for professional search by skill
 * Following the same pattern as SkillUiState
 */
data class SearchProfessionalBySkillUIState(
    val searchQuery: String = "",
    val professionals: List<ProfessionalSearchBySkill> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val hasSearched: Boolean = false
)
