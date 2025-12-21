package com.br.xbizitwork.data.remote.schedule.datasource

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.schedule.api.ScheduleApiService
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateAvailabilityRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse
import com.br.xbizitwork.data.remote.schedule.dtos.responses.TimeSlotResponse
import javax.inject.Inject

/**
 * Implementação do ScheduleRemoteDataSource
 */
class ScheduleRemoteDataSourceImpl @Inject constructor(
    private val apiService: ScheduleApiService
) : ScheduleRemoteDataSource {
    
    override suspend fun createSchedule(request: CreateScheduleRequest): ApiResponse<ScheduleResponse> {
        return apiService.createSchedule(request)
    }
    
    override suspend fun getProfessionalSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>> {
        return apiService.getProfessionalSchedules(professionalId)
    }
    
    override suspend fun getScheduleById(scheduleId: String): ApiResponse<ScheduleResponse> {
        return apiService.getScheduleById(scheduleId)
    }
    
    override suspend fun updateSchedule(
        scheduleId: String,
        request: UpdateScheduleRequest
    ): ApiResponse<ScheduleResponse> {
        return apiService.updateSchedule(scheduleId, request)
    }
    
    override suspend fun deleteSchedule(scheduleId: String): ApiResponse<Unit> {
        return apiService.deleteSchedule(scheduleId)
    }
    
    override suspend fun updateAvailability(
        scheduleId: String,
        request: UpdateAvailabilityRequest
    ): ApiResponse<ScheduleResponse> {
        return apiService.updateAvailability(scheduleId, request)
    }
    
    override suspend fun getAvailableTimeSlots(
        scheduleId: String,
        date: String,
        dayOfWeek: String
    ): ApiResponse<List<TimeSlotResponse>> {
        return apiService.getAvailableTimeSlots(scheduleId, date, dayOfWeek)
    }
    
    override suspend fun getActiveSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>> {
        return apiService.getActiveSchedules(professionalId)
    }
}
