package com.br.xbizitwork.data.remote.schedule.datasource

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateAvailabilityRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse
import com.br.xbizitwork.data.remote.schedule.dtos.responses.TimeSlotResponse

/**
 * Remote Data Source para Schedule
 */
interface ScheduleRemoteDataSource {
    suspend fun createSchedule(request: CreateScheduleRequest): ApiResponse<ScheduleResponse>
    suspend fun getProfessionalSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>>
    suspend fun getScheduleById(scheduleId: String): ApiResponse<ScheduleResponse>
    suspend fun updateSchedule(scheduleId: String, request: UpdateScheduleRequest): ApiResponse<ScheduleResponse>
    suspend fun deleteSchedule(scheduleId: String): ApiResponse<Unit>
    suspend fun updateAvailability(scheduleId: String, request: UpdateAvailabilityRequest): ApiResponse<ScheduleResponse>
    suspend fun getAvailableTimeSlots(scheduleId: String, date: String, dayOfWeek: String): ApiResponse<List<TimeSlotResponse>>
    suspend fun getActiveSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>>
}
