package com.br.xbizitwork.ui.presentation.features.searchprofessionals.state

import androidx.compose.foundation.text.input.TextFieldState
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill

/**
 * UI State for professional search by skill
 * Following the same pattern as SearchSchedulesUIState
 */
data class SearchProfessionalsUiState(
    val queryTextState: TextFieldState = TextFieldState(),
    val professionals: List<ProfessionalSearchBySkill> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val searchIsEmpty: Boolean = true
)
