package com.br.xbizitwork.data.remote.schedule.mappers

import com.br.xbizitwork.data.remote.schedule.dtos.requests.AvailabilityRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
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
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Mappers para Schedule
 */

// Domain -> Request
// NOTA: CreateScheduleRequest agora usa formato diferente (userId, categoryId, specialtyId, weekDays, startTime, endTime, status)
// e é criado diretamente no ViewModel, não precisa de mapper do Schedule completo
/*
fun Schedule.toCreateRequest(): CreateScheduleRequest {
    return CreateScheduleRequest(
        professionalId = professionalId,
        category = category,
        specialty = specialty,
        availability = availability.toRequest(),
        serviceDurationMinutes = serviceDurationMinutes
    )
}
*/

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
        effectiveFrom = effectiveFrom.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        effectiveUntil = effectiveUntil?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
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
        startTime = startTime.format(DateTimeFormatter.ISO_LOCAL_TIME),
        endTime = endTime.format(DateTimeFormatter.ISO_LOCAL_TIME),
        isActive = isActive,
        breakStartTime = breakStartTime?.format(DateTimeFormatter.ISO_LOCAL_TIME),
        breakEndTime = breakEndTime?.format(DateTimeFormatter.ISO_LOCAL_TIME)
    )
}

// Response -> Domain
fun ScheduleResponse.toDomain(): Schedule {
    return Schedule(
        id = id,
        professionalId = professionalId,
        category = category,
        specialty = specialty,
        availability = availability.toDomain(),
        timeSlots = timeSlots.map { it.toDomain() },
        serviceDurationMinutes = serviceDurationMinutes,
        isActive = isActive,
        createdAt = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        updatedAt = LocalDateTime.parse(updatedAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    )
}

fun AvailabilityResponse.toDomain(): Availability {
    return Availability(
        id = id,
        professionalId = professionalId,
        workingHours = workingHours.map { it.toDomain() },
        effectiveFrom = LocalDateTime.parse(effectiveFrom, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        effectiveUntil = effectiveUntil?.let { LocalDateTime.parse(it, DateTimeFormatter.ISO_LOCAL_DATE_TIME) },
        isActive = isActive,
        createdAt = LocalDateTime.parse(createdAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME),
        updatedAt = LocalDateTime.parse(updatedAt, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    )
}

fun WorkingHoursResponse.toDomain(): WorkingHours {
    return WorkingHours(
        dayOfWeek = DayOfWeek.valueOf(dayOfWeek),
        startTime = LocalTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_TIME),
        endTime = LocalTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_TIME),
        isActive = isActive,
        breakStartTime = breakStartTime?.let { LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME) },
        breakEndTime = breakEndTime?.let { LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME) }
    )
}

fun TimeSlotResponse.toDomain(): TimeSlot {
    return TimeSlot(
        id = id,
        dayOfWeek = DayOfWeek.valueOf(dayOfWeek),
        startTime = LocalTime.parse(startTime, DateTimeFormatter.ISO_LOCAL_TIME),
        endTime = LocalTime.parse(endTime, DateTimeFormatter.ISO_LOCAL_TIME),
        isAvailable = isAvailable
    )
}
