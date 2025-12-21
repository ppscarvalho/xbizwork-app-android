package com.br.xbizitwork.data.remote.schedule.dtos.requests

import kotlinx.serialization.Serializable

/**
 * Request para criar uma agenda
 * Formato real da API: POST /api/v1/schedule/create
 */
@Serializable
data class CreateScheduleRequest(
    val userId: Int,
    val categoryId: Int,
    val specialtyId: Int,
    val weekDays: List<Int>,
    val startTime: String,
    val endTime: String,
    val status: Boolean
)

// ==================== ANTIGOS (manter para outras operações) ====================

@Serializable
data class WorkingHoursRequest(
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String,
    val isActive: Boolean = true,
    val breakStartTime: String? = null,
    val breakEndTime: String? = null
)

@Serializable
data class AvailabilityRequest(
    val workingHours: List<WorkingHoursRequest>,
    val effectiveFrom: String,
    val effectiveUntil: String? = null
)

@Serializable
data class UpdateScheduleRequest(
    val category: String,
    val specialty: String,
    val availability: AvailabilityRequest,
    val serviceDurationMinutes: Int,
    val isActive: Boolean
)

@Serializable
data class UpdateAvailabilityRequest(
    val availability: AvailabilityRequest
)
