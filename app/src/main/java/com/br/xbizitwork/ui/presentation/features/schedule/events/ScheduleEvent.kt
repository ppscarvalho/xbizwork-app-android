package com.br.xbizitwork.ui.presentation.features.schedule.events

/**
 * Eventos da ScheduleScreen
 */
sealed class ScheduleEvent {
    // Seleção de campos
    data class OnCategorySelected(val categoryId: Int) : ScheduleEvent()
    data class OnSubcategorySelected(val subcategoryId: Int) : ScheduleEvent()
    data class OnDayOfWeekSelected(val dayOfWeekId: Int) : ScheduleEvent()
    data class OnStartTimeSelected(val startTime: String) : ScheduleEvent()
    data class OnEndTimeSelected(val endTime: String) : ScheduleEvent()

    // Ações
    object OnAddScheduleItem : ScheduleEvent()
    data class OnRemoveScheduleItem(
        val categoryId: Int,
        val subcategoryId: Int,
        val dayOfWeekId: Int,
        val startTime: String
    ) : ScheduleEvent()
    object OnSaveSchedule : ScheduleEvent()

    // Ciclo de vida
    object OnScreenLoad : ScheduleEvent()
}
