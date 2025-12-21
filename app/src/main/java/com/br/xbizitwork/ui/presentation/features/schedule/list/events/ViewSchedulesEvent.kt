package com.br.xbizitwork.ui.presentation.features.schedule.list.events

/**
 * Eventos da tela de listar agendas
 */
sealed interface ViewSchedulesEvent {
    data object OnRefresh : ViewSchedulesEvent
    data class OnScheduleClick(val scheduleId: String) : ViewSchedulesEvent
    data class OnDeleteSchedule(val scheduleId: String) : ViewSchedulesEvent
    data object OnCreateScheduleClick : ViewSchedulesEvent
}
