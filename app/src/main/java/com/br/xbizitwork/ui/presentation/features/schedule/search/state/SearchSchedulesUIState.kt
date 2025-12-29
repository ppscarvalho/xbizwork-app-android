package com.br.xbizitwork.ui.presentation.features.schedule.search.state

import androidx.compose.foundation.text.input.TextFieldState
import com.br.xbizitwork.domain.model.schedule.Schedule

data class SearchSchedulesUIState(
    val queryTextState: TextFieldState = TextFieldState(),
    val schedules: List<Schedule> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false,
    val searchIsEmpty: Boolean = false,
)
