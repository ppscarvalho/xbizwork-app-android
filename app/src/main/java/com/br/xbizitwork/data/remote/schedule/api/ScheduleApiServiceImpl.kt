package com.br.xbizitwork.data.remote.schedule.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.core.model.api.ApiResultResponse
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateAvailabilityRequest
import com.br.xbizitwork.data.remote.schedule.dtos.requests.UpdateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse
import com.br.xbizitwork.data.remote.schedule.dtos.responses.TimeSlotResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ScheduleApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : ScheduleApiService {
    
    override suspend fun createSchedule(request: CreateScheduleRequest): ApiResultResponse {
        val response = httpClient.post("schedule/create") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }
    
    override suspend fun getProfessionalSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>> {
        val response = httpClient.get("schedule/professional/$professionalId")
        return response.body()
    }
    
    override suspend fun getScheduleById(scheduleId: String): ApiResponse<ScheduleResponse> {
        val response = httpClient.get("schedule/$scheduleId")
        return response.body()
    }
    
    override suspend fun updateSchedule(
        scheduleId: String,
        request: UpdateScheduleRequest
    ): ApiResultResponse {
        val response = httpClient.put("schedule/$scheduleId") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }
    
    override suspend fun deleteSchedule(scheduleId: String): ApiResultResponse {
        val response = httpClient.delete("schedule/$scheduleId")
        return response.body()
    }
    
    override suspend fun updateAvailability(
        scheduleId: String,
        request: UpdateAvailabilityRequest
    ): ApiResultResponse {
        val response = httpClient.put("schedule/$scheduleId/availability") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }
    
    override suspend fun getAvailableTimeSlots(
        scheduleId: String,
        date: String,
        dayOfWeek: String
    ): ApiResponse<List<TimeSlotResponse>> {
        val response = httpClient.get("schedule/$scheduleId/timeslots") {
            url {
                parameters.append("date", date)
                parameters.append("dayOfWeek", dayOfWeek)
            }
        }
        return response.body()
    }
    
    override suspend fun getActiveSchedules(professionalId: String): ApiResponse<List<ScheduleResponse>> {
        val response = httpClient.get("schedule/professional/$professionalId/active")
        return response.body()
    }

    override suspend fun validateSchedule(request: CreateScheduleRequest): ApiResultResponse {
        val response = httpClient.post("schedule/validate") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }

    override suspend fun getCategoryByDescription(description: String): ApiResponse<List<ScheduleResponse>> {
        val response = httpClient.get("schedule/search?description=$description")
        return response.body()
    }
}
