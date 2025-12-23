package com.br.xbizitwork.domain.validations.schedule

import com.br.xbizitwork.domain.model.schedule.DayOfWeek

sealed class ScheduleValidationError {
    object Valid : ScheduleValidationError()
    object EmptyCategory : ScheduleValidationError()
    object EmptySpecialty : ScheduleValidationError()
    object NoWorkingHours : ScheduleValidationError()
    object NoActiveDays : ScheduleValidationError()
    data class InvalidWorkingHours(val days: List<DayOfWeek>) : ScheduleValidationError()
}

