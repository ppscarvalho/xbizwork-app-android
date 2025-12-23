package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.validations.schedule.ScheduleValidationError

interface ValidateScheduleUseCase {
    operator fun invoke(schedule: Schedule): ScheduleValidationError
}

class ValidateScheduleUseCaseImpl : ValidateScheduleUseCase {
    override fun invoke(schedule: Schedule): ScheduleValidationError {
        // Valida categoria
        if (schedule.category.isBlank()) {
            return ScheduleValidationError.EmptyCategory
        }

        // Valida especialidade
        if (schedule.specialty.isBlank()) {
            return ScheduleValidationError.EmptySpecialty
        }

        // Valida se há pelo menos um dia de trabalho
        if (schedule.availability.workingHours.isEmpty()) {
            return ScheduleValidationError.NoWorkingHours
        }

        // Valida se há pelo menos um dia ativo
        val hasActiveDay = schedule.availability.workingHours.any { it.isActive }
        if (!hasActiveDay) {
            return ScheduleValidationError.NoActiveDays
        }

        // Valida horários dos dias ativos
        val invalidHours = schedule.availability.workingHours
            .filter { it.isActive && !it.isValid() }

        if (invalidHours.isNotEmpty()) {
            return ScheduleValidationError.InvalidWorkingHours(invalidHours.map { it.dayOfWeek })
        }

        return ScheduleValidationError.Valid
    }
}

