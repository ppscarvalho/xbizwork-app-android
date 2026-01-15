package com.br.xbizitwork.data.remote.schedule.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateAvailabilityRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse
import com.br.xbizitwork.data.remote.schedule.dtos.responses.TimeSlotResponse

/**
 * API Service para gerenciamento de agendas
 *
 * Padrão de retorno:
 * - CREATE/UPDATE/DELETE: ApiResultResponse (isSuccessful, message)
 * - GET/LIST: ApiResponse<T> (data, isSuccessful, message)
 */
interface ScheduleApiService {
    
    /**
     * POST /api/schedules - CREATE
     */
    suspend fun createSchedule(request: CreateScheduleRequest): ApiResultResponse

    /**
     * GET /api/schedules/professional/{professionalId} - LIST
     */
    suspend fun getProfessionalSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>>
    
    /**
     * GET /api/schedules/{scheduleId} - GET
     */
    suspend fun getScheduleById(scheduleId: String): ApiResponse<ScheduleResponse>
    
    /**
     * PUT /api/schedules/{scheduleId} - UPDATE
     */
    suspend fun updateSchedule(scheduleId: String, request: UpdateScheduleRequest): ApiResultResponse

    /**
     * DELETE /api/schedules/{scheduleId} - DELETE
     */
    suspend fun deleteSchedule(scheduleId: String): ApiResultResponse

    /**
     * PUT /api/schedules/{scheduleId}/availability - UPDATE
     */
    suspend fun updateAvailability(
        scheduleId: String,
        request: UpdateAvailabilityRequest
    ): ApiResultResponse

    /**
     * GET /api/schedules/{scheduleId}/timeslots - LIST
     */
    suspend fun getAvailableTimeSlots(
        scheduleId: String,
        date: String,
        dayOfWeek: String
    ): ApiResponse<List<TimeSlotResponse>>
    
    /**
     * GET /api/schedules/professional/{professionalId}/active - LIST
     */
    suspend fun getActiveSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>>

    /**
     * POST /api/schedules/validate - Validação
     * Retorna apenas isSuccessful e message
     */
    suspend fun validateSchedule(request: CreateScheduleRequest): ApiResultResponse

    /**
     * GET /api/schedules/category/{description} - LIST
     */
    suspend fun getCategoryByDescription(description: String): ApiResponse<List<ScheduleResponse>>
}
