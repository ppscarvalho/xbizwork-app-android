package com.br.xbizitwork.ui.presentation.features.schedule.list.state

import com.br.xbizitwork.domain.model.schedule.Schedule

/**
 * Estado da tela de listar agendas
 */
data class ViewSchedulesUIState(
    val schedules: List<Schedule> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isEmpty: Boolean = false
)
