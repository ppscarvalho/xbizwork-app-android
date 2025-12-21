package com.br.xbizitwork.data.remote.schedule.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateAvailabilityRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse
import com.br.xbizitwork.data.remote.schedule.dtos.responses.TimeSlotResponse

/**
 * API Service para gerenciamento de agendas
 */
interface ScheduleApiService {
    
    /**
     * POST /api/schedules
     */
    suspend fun createSchedule(request: CreateScheduleRequest): ApiResponse<ScheduleResponse>
    
    /**
     * GET /api/schedules/professional/{professionalId}
     */
    suspend fun getProfessionalSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>>
    
    /**
     * GET /api/schedules/{scheduleId}
     */
    suspend fun getScheduleById(scheduleId: String): ApiResponse<ScheduleResponse>
    
    /**
     * PUT /api/schedules/{scheduleId}
     */
    suspend fun updateSchedule(scheduleId: String, request: UpdateScheduleRequest): ApiResponse<ScheduleResponse>
    
    /**
     * DELETE /api/schedules/{scheduleId}
     */
    suspend fun deleteSchedule(scheduleId: String): ApiResponse<Unit>
    
    /**
     * PUT /api/schedules/{scheduleId}/availability
     */
    suspend fun updateAvailability(
        scheduleId: String,
        request: UpdateAvailabilityRequest
    ): ApiResponse<ScheduleResponse>
    
    /**
     * GET /api/schedules/{scheduleId}/timeslots?date=2025-12-21&dayOfWeek=MONDAY
     */
    suspend fun getAvailableTimeSlots(
        scheduleId: String,
        date: String,
        dayOfWeek: String
    ): ApiResponse<List<TimeSlotResponse>>
    
    /**
     * GET /api/schedules/professional/{professionalId}/active
     */
    suspend fun getActiveSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>>
}
