package com.br.xbizitwork.data.remote.schedule.dtos.responses

import kotlinx.serialization.Serializable

@Serializable
data class CategoryResponse(
    val id: Int,
    val description: String
)

@Serializable
data class SpecialtyResponse(
    val id: Int,
    val description: String
)

@Serializable
data class UserResponse(
    val id: Int,
    val name: String
)

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
    val scheduleId: Int,
    val userId: Int,
    val categoryId: Int,
    val specialtyId: Int,
    val weekDays: List<Int>,
    val startTime: String,
    val endTime: String,
    val status: Boolean,
    val user: UserResponse,
    val category: CategoryResponse,
    val specialty: SpecialtyResponse
)
