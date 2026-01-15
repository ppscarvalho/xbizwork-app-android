package com.br.xbizitwork.data.remote.schedule.datasource

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateAvailabilityRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse
import com.br.xbizitwork.data.remote.schedule.dtos.responses.TimeSlotResponse

/**
 * Remote Data Source para Schedule
 *
 * Padrão:
 * - CREATE/UPDATE/DELETE retorna ApiResultResponse
 * - GET/LIST retorna ApiResponse<T>
 */
interface ScheduleRemoteDataSource {
    suspend fun createSchedule(request: CreateScheduleRequest): ApiResultResponse
    suspend fun getProfessionalSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>>
    suspend fun getScheduleById(scheduleId: String): ApiResponse<ScheduleResponse>
    suspend fun updateSchedule(scheduleId: String, request: UpdateScheduleRequest): ApiResultResponse
    suspend fun deleteSchedule(scheduleId: String): ApiResultResponse
    suspend fun updateAvailability(scheduleId: String, request: UpdateAvailabilityRequest): ApiResultResponse
    suspend fun getAvailableTimeSlots(scheduleId: String, date: String, dayOfWeek: String): ApiResponse<List<TimeSlotResponse>>
    suspend fun getActiveSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>>
    suspend fun validateSchedule(request: CreateScheduleRequest): ApiResultResponse
    suspend fun getCategoryByDescription(description: String): ApiResponse<List<ScheduleResponse>>
}
