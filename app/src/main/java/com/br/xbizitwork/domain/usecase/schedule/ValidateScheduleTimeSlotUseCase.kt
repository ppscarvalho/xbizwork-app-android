package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.ui.presentation.features.schedule.create.state.ScheduleTimeSlot
import javax.inject.Inject

/**
 * Use Case para validar horários de agenda localmente (antes de chamar o backend)
 * Responsável por todas as regras de negócio de validação temporal
 */
interface ValidateScheduleTimeSlotUseCase {
    operator fun invoke(parameters: Parameters): ValidationResult

    data class Parameters(
        val startTime: String,
        val endTime: String,
        val categoryId: Int,
        val specialtyId: Int,
        val weekDay: Int,
        val existingSlots: List<ScheduleTimeSlot>
    )

    sealed class ValidationResult {
        object Valid : ValidationResult()
        data class Invalid(val message: String) : ValidationResult()
    }
}

/**
 * Implementação do ValidateScheduleTimeSlotUseCase
 */
class ValidateScheduleTimeSlotUseCaseImpl @Inject constructor() : ValidateScheduleTimeSlotUseCase {

    override fun invoke(parameters: ValidateScheduleTimeSlotUseCase.Parameters): ValidateScheduleTimeSlotUseCase.ValidationResult {
        // Validação 1: Hora final > Hora inicial
        val startTimeInMinutes = timeToMinutes(parameters.startTime)
        val endTimeInMinutes = timeToMinutes(parameters.endTime)

        if (endTimeInMinutes <= startTimeInMinutes) {
            return ValidateScheduleTimeSlotUseCase.ValidationResult.Invalid(
                "❌ Hora final deve ser maior que hora inicial!"
            )
        }

        // Validação 2: Duplicidade
        val isDuplicate = parameters.existingSlots.any { slot ->
            slot.categoryId == parameters.categoryId &&
            slot.specialtyId == parameters.specialtyId &&
            slot.weekDay == parameters.weekDay &&
            slot.startTime == parameters.startTime &&
            slot.endTime == parameters.endTime
        }

        if (isDuplicate) {
            return ValidateScheduleTimeSlotUseCase.ValidationResult.Invalid(
                "❌ Este horário já foi adicionado!"
            )
        }

        // Validação 3: Sobreposição e sequência
        val hasOverlapOrSequential = parameters.existingSlots.any { slot ->
            if (slot.categoryId == parameters.categoryId &&
                slot.specialtyId == parameters.specialtyId &&
                slot.weekDay == parameters.weekDay) {

                val slotStartMinutes = timeToMinutes(slot.startTime)
                val slotEndMinutes = timeToMinutes(slot.endTime)

                val startsInside = startTimeInMinutes >= slotStartMinutes &&
                                 startTimeInMinutes < slotEndMinutes
                val endsInside = endTimeInMinutes > slotStartMinutes &&
                               endTimeInMinutes <= slotEndMinutes
                val encompasses = startTimeInMinutes <= slotStartMinutes &&
                                endTimeInMinutes >= slotEndMinutes

                val isSequentialStart = startTimeInMinutes == slotEndMinutes
                val isSequentialEnd = endTimeInMinutes == slotStartMinutes

                startsInside || endsInside || encompasses || isSequentialStart || isSequentialEnd
            } else {
                false
            }
        }

        if (hasOverlapOrSequential) {
            return ValidateScheduleTimeSlotUseCase.ValidationResult.Invalid(
                "❌ Horários devem ter intervalo entre eles!"
            )
        }

        return ValidateScheduleTimeSlotUseCase.ValidationResult.Valid
    }

    private fun timeToMinutes(time: String): Int {
        val hour = time.substringBefore(":").toIntOrNull() ?: 0
        val minute = time.substringAfter(":").toIntOrNull() ?: 0
        return hour * 60 + minute
    }
}

