package com.br.xbizitwork.data.remote.schedule.mappers

import android.annotation.SuppressLint
import com.br.xbizitwork.data.remote.schedule.dtos.requests.AvailabilityRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateAvailabilityRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.WorkingHoursRequest
import com.br.xbizitwork.data.remote.schedule.dtos.responses.AvailabilityResponse
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse
import com.br.xbizitwork.data.remote.schedule.dtos.responses.TimeSlotResponse
import com.br.xbizitwork.data.remote.schedule.dtos.responses.WorkingHoursResponse
import com.br.xbizitwork.domain.model.schedule.Availability
import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.model.schedule.TimeSlot
import com.br.xbizitwork.domain.model.schedule.WorkingHours
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Mappers para Schedule compatível com API 24+
 *
 * NOTA: Os warnings de "requires API level 26" são FALSOS POSITIVOS!
 * O projeto usa desugaring (coreLibraryDesugaring) que faz backport de java.time para API 24+
 * O código FUNCIONA em runtime mesmo mostrando warnings no IDE.
 */

// Formatters para conversão String <-> Date
private val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
private val timeFormatter = SimpleDateFormat("HH:mm", Locale.US)

// Extension functions usando toString() que é suportado pelo desugaring
@SuppressLint("NewApi") // Desugaring faz backport de java.time
private fun LocalDateTime.toIsoString(): String {
    // toString() de LocalDateTime retorna formato ISO e funciona com desugaring
    return this.toString()
}

@SuppressLint("NewApi") // Desugaring faz backport de java.time
private fun LocalTime.toTimeString(): String {
    // toString() de LocalTime retorna "HH:mm:ss" - vamos pegar só HH:mm
    val timeStr = this.toString()
    return if (timeStr.length >= 5) timeStr.substring(0, 5) else timeStr
}

@SuppressLint("NewApi") // Desugaring faz backport de java.time
private fun String.toLocalDateTime(): LocalDateTime {
    return try {
        // LocalDateTime.parse() funciona com desugaring
        LocalDateTime.parse(this)
    } catch (_: Exception) {
        // Valor padrão em caso de erro
        LocalDateTime.parse("2024-01-01T00:00:00")
    }
}

@SuppressLint("NewApi") // Desugaring faz backport de java.time
private fun String.toLocalTime(): LocalTime {
    return try {
        // LocalTime.parse() funciona com desugaring
        LocalTime.parse(this)
    } catch (_: Exception) {
        // Valor padrão em caso de erro
        LocalTime.parse("00:00")
    }
}

@SuppressLint("NewApi") // Desugaring faz backport de java.time
private fun getCurrentLocalDateTime(): LocalDateTime {
    // LocalDateTime.now() funciona com desugaring
    return LocalDateTime.now()
}

// Domain -> Request
fun Schedule.toUpdateRequest(): UpdateScheduleRequest {
    return UpdateScheduleRequest(
        category = category,
        specialty = specialty,
        availability = availability.toRequest(),
        serviceDurationMinutes = serviceDurationMinutes,
        isActive = isActive
    )
}

fun Availability.toRequest(): AvailabilityRequest {
    return AvailabilityRequest(
        workingHours = workingHours.map { it.toRequest() },
        effectiveFrom = effectiveFrom.toIsoString(),
        effectiveUntil = effectiveUntil?.toIsoString()
    )
}

fun Availability.toUpdateRequest(): UpdateAvailabilityRequest {
    return UpdateAvailabilityRequest(
        availability = this.toRequest()
    )
}

fun WorkingHours.toRequest(): WorkingHoursRequest {
    return WorkingHoursRequest(
        dayOfWeek = dayOfWeek.name,
        startTime = startTime.toTimeString(),
        endTime = endTime.toTimeString(),
        isActive = isActive,
        breakStartTime = breakStartTime?.toTimeString(),
        breakEndTime = breakEndTime?.toTimeString()
    )
}

// Response -> Domain
@SuppressLint("NewApi")
fun ScheduleResponse.toDomain(): Schedule {
    val now = getCurrentLocalDateTime()

    // Converter weekDays (List<Int>) em WorkingHours
    val workingHours = weekDays.map { dayNumber ->
        val dayOfWeek = when (dayNumber) {
            0 -> DayOfWeek.SUNDAY
            1 -> DayOfWeek.MONDAY
            2 -> DayOfWeek.TUESDAY
            3 -> DayOfWeek.WEDNESDAY
            4 -> DayOfWeek.THURSDAY
            5 -> DayOfWeek.FRIDAY
            6 -> DayOfWeek.SATURDAY
            else -> DayOfWeek.MONDAY
        }

        WorkingHours(
            dayOfWeek = dayOfWeek,
            startTime = startTime.toLocalTime(),
            endTime = endTime.toLocalTime(),
            isActive = status,
            breakStartTime = null,
            breakEndTime = null
        )
    }

    val availability = Availability(
        id = scheduleId.toString(),
        professionalId = userId.toString(),
        workingHours = workingHours, // ← Agora preenchido!
        effectiveFrom = now,
        effectiveUntil = null,
        isActive = status,
        createdAt = now,
        updatedAt = now
    )

    return Schedule(
        id = scheduleId.toString(),
        professionalId = userId.toString(),
        category = category.description,
        specialty = specialty.description,
        availability = availability,
        timeSlots = emptyList(),
        serviceDurationMinutes = 60,
        isActive = status,
        createdAt = now,
        updatedAt = now
    )
}

fun AvailabilityResponse.toDomain(): Availability {
    return Availability(
        id = id,
        professionalId = professionalId,
        workingHours = workingHours.map { it.toDomain() },
        effectiveFrom = effectiveFrom.toLocalDateTime(),
        effectiveUntil = effectiveUntil?.toLocalDateTime(),
        isActive = isActive,
        createdAt = createdAt.toLocalDateTime(),
        updatedAt = updatedAt.toLocalDateTime()
    )
}

fun WorkingHoursResponse.toDomain(): WorkingHours {
    return WorkingHours(
        dayOfWeek = DayOfWeek.valueOf(dayOfWeek),
        startTime = startTime.toLocalTime(),
        endTime = endTime.toLocalTime(),
        isActive = isActive,
        breakStartTime = breakStartTime?.toLocalTime(),
        breakEndTime = breakEndTime?.toLocalTime()
    )
}

fun TimeSlotResponse.toDomain(): TimeSlot {
    return TimeSlot(
        id = id,
        dayOfWeek = DayOfWeek.valueOf(dayOfWeek),
        startTime = startTime.toLocalTime(),
        endTime = endTime.toLocalTime(),
        isAvailable = isAvailable
    )
}

