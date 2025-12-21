package com.br.xbizitwork.data.remote.schedule.dtos.responses

import kotlinx.serialization.Serializable

@Serializable
data class TimeSlotResponse(
    val id: String,
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String,
    val isAvailable: Boolean
)

@Serializable
data class WorkingHoursResponse(
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String,
    val isActive: Boolean,
    val breakStartTime: String? = null,
    val breakEndTime: String? = null
)

@Serializable
data class AvailabilityResponse(
    val id: String,
    val professionalId: String,
    val workingHours: List<WorkingHoursResponse>,
    val effectiveFrom: String,
    val effectiveUntil: String? = null,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class ScheduleResponse(
    val id: String,
    val professionalId: String,
    val category: String,
    val specialty: String,
    val availability: AvailabilityResponse,
    val timeSlots: List<TimeSlotResponse> = emptyList(),
    val serviceDurationMinutes: Int,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String
)
