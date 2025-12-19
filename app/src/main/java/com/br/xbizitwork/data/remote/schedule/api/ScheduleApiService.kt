package com.br.xbizitwork.data.remote.schedule.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.schedule.dtos.request.SaveScheduleRequest

/**
 * API Service para agendas
 */
interface ScheduleApiService {
    /**
     * Salva a agenda do profissional
     */
    suspend fun saveSchedule(request: SaveScheduleRequest): ApiResponse<Unit>
}
