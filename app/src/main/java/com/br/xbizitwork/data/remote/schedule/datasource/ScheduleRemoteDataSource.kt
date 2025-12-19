package com.br.xbizitwork.data.remote.schedule.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.schedule.dtos.request.SaveScheduleRequest

/**
 * Interface para acesso remoto aos dados de agendas
 */
interface ScheduleRemoteDataSource {
    suspend fun saveSchedule(request: SaveScheduleRequest): DefaultResult<Unit>
}
