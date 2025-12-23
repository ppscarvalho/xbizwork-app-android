package com.br.xbizitwork.ui.presentation.features.schedule.agenda.state

import com.br.xbizitwork.domain.model.schedule.Schedule

/**
 * Estado da tela de agenda do profissional
 */
data class ProfessionalAgendaUIState(
    val schedules: List<Schedule> = emptyList(),
    val selectedDate: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
