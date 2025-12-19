package com.br.xbizitwork.data.remote.schedule.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.schedule.dtos.request.SaveScheduleRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ScheduleApiServiceImpl @Inject constructor(
    private val httpClient: HttpClient
) : ScheduleApiService {
    override suspend fun saveSchedule(request: SaveScheduleRequest): ApiResponse<Unit> {
        val response = httpClient.post("schedules/save") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        return response.body()
    }
}
