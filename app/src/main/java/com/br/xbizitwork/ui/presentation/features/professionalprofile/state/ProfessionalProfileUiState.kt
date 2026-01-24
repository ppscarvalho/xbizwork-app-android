package com.br.xbizitwork.ui.presentation.features.professionalprofile.state

import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill

/**
 * UI State for professional profile screen
 * Following the same pattern as SearchProfessionalsUiState
 */
data class ProfessionalProfileUiState(
    val professional: ProfessionalSearchBySkill? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
