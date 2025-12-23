package com.br.xbizitwork.ui.presentation.features.schedule.create.state

/**
 * Representa um horário temporário antes de ser salvo
 */
data class ScheduleTimeSlot(
    val id: String,
    val categoryId: Int,
    val categoryName: String,
    val specialtyId: Int,
    val specialtyName: String,
    val weekDay: Int,
    val weekDayName: String,
    val startTime: String,
    val endTime: String
)
