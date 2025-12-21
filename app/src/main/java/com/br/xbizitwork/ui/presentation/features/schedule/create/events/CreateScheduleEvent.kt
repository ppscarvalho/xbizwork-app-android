package com.br.xbizitwork.ui.presentation.features.schedule.create.events

/**
 * Eventos da tela de criar agenda - Novo fluxo UX
 */
sealed interface CreateScheduleEvent {
    // Carregar dados
    data object OnLoadCategories : CreateScheduleEvent
    data class OnLoadSpecialties(val categoryId: Int) : CreateScheduleEvent
    
    // Seleções
    data class OnCategorySelected(val categoryId: Int, val categoryName: String) : CreateScheduleEvent
    data class OnSpecialtySelected(val specialtyId: Int, val specialtyName: String) : CreateScheduleEvent
    data class OnWeekDaySelected(val weekDay: Int, val weekDayName: String) : CreateScheduleEvent
    data class OnStartTimeChanged(val time: String) : CreateScheduleEvent
    data class OnEndTimeChanged(val time: String) : CreateScheduleEvent
    
    // Ações
    data object OnAddTimeSlot : CreateScheduleEvent
    data class OnRemoveTimeSlot(val slotId: String) : CreateScheduleEvent
    data object OnSaveSchedule : CreateScheduleEvent
    
    // UI
    data object OnDismissError : CreateScheduleEvent
}

