package com.br.xbizitwork.ui.presentation.features.schedule.list.events

/**
 * Eventos da tela de listar agendas
 */
sealed interface ListSchedulesEvent {
    data object OnRefresh : ListSchedulesEvent
    data class OnScheduleClick(val scheduleId: String) : ListSchedulesEvent
    data class OnDeleteSchedule(val scheduleId: String) : ListSchedulesEvent
    data object OnCreateScheduleClick : ListSchedulesEvent
}
