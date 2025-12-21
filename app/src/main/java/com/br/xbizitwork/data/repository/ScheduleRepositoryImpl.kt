package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.schedule.datasource.ScheduleRemoteDataSource
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse
import com.br.xbizitwork.data.remote.schedule.mappers.*
import com.br.xbizitwork.domain.model.schedule.Availability
import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.model.schedule.TimeSlot
import com.br.xbizitwork.domain.repository.ScheduleRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

/**
 * Implementação do ScheduleRepository
 */
class ScheduleRepositoryImpl @Inject constructor(
    private val remoteDataSource: ScheduleRemoteDataSource
) : ScheduleRepository {
    
    override suspend fun createSchedule(schedule: Schedule): DefaultResult<Schedule> {
        // NOTA: Método antigo mantido para compatibilidade, mas não funciona com novo formato da API
        // Use createScheduleFromRequest() ao invés deste
        throw UnsupportedOperationException("Use createScheduleFromRequest() para criar agendas com o novo formato da API")
    }
    
    override suspend fun createScheduleFromRequest(request: CreateScheduleRequest): DefaultResult<ScheduleResponse> {
        val response = remoteDataSource.createSchedule(request)
        return when {
            response.isSuccessful && response.data != null -> {
                DefaultResult.Success(response.data)
            }
            else -> {
                DefaultResult.Error(null, response.message)
            }
        }
    }
    
    override suspend fun getProfessionalSchedules(professionalId: String): DefaultResult<List<Schedule>> {
        val response = remoteDataSource.getProfessionalSchedules(professionalId)
        return when {
            response.isSuccessful && response.data != null -> {
                DefaultResult.Success(response.data.map { it.toDomain() })
            }
            else -> {
                DefaultResult.Error(null, response.message)
            }
        }
    }
    
    override suspend fun getScheduleById(scheduleId: String): DefaultResult<Schedule> {
        val response = remoteDataSource.getScheduleById(scheduleId)
        return when {
            response.isSuccessful && response.data != null -> {
                DefaultResult.Success(response.data.toDomain())
            }
            else -> {
                DefaultResult.Error(null, response.message)
            }
        }
    }
    
    override suspend fun updateSchedule(schedule: Schedule): DefaultResult<Schedule> {
        val response = remoteDataSource.updateSchedule(schedule.id, schedule.toUpdateRequest())
        return when {
            response.isSuccessful && response.data != null -> {
                DefaultResult.Success(response.data.toDomain())
            }
            else -> {
                DefaultResult.Error(null, response.message)
            }
        }
    }
    
    override suspend fun deleteSchedule(scheduleId: String): DefaultResult<Unit> {
        val response = remoteDataSource.deleteSchedule(scheduleId)
        return when {
            response.isSuccessful -> {
                DefaultResult.Success(Unit)
            }
            else -> {
                DefaultResult.Error(null, response.message)
            }
        }
    }
    
    override suspend fun updateAvailability(
        scheduleId: String,
        availability: Availability
    ): DefaultResult<Schedule> {
        val response = remoteDataSource.updateAvailability(
            scheduleId,
            availability.toUpdateRequest()
        )
        return when {
            response.isSuccessful && response.data != null -> {
                DefaultResult.Success(response.data.toDomain())
            }
            else -> {
                DefaultResult.Error(null, response.message)
            }
        }
    }
    
    override suspend fun getAvailableTimeSlots(
        scheduleId: String,
        date: java.time.LocalDate,
        dayOfWeek: DayOfWeek
    ): DefaultResult<List<TimeSlot>> {
        // Converter LocalDate para String sem usar métodos de API 26
        // LocalDate toString() retorna formato ISO (yyyy-MM-dd) e funciona em todas APIs
        val dateStr = date.toString()

        val response = remoteDataSource.getAvailableTimeSlots(
            scheduleId,
            dateStr,
            dayOfWeek.name
        )
        return when {
            response.isSuccessful && response.data != null -> {
                DefaultResult.Success(response.data.map { it.toDomain() })
            }
            else -> {
                DefaultResult.Error(null, response.message)
            }
        }
    }
    
    override suspend fun blockTimeSlot(
        scheduleId: String,
        timeSlot: TimeSlot
    ): DefaultResult<TimeSlot> {
        // TODO: Implementar quando backend estiver pronto
        return DefaultResult.Error(null, "Funcionalidade não implementada")
    }
    
    override suspend fun releaseTimeSlot(
        scheduleId: String,
        timeSlotId: String
    ): DefaultResult<TimeSlot> {
        // TODO: Implementar quando backend estiver pronto
        return DefaultResult.Error(null, "Funcionalidade não implementada")
    }
    
    override suspend fun getActiveSchedules(professionalId: String): DefaultResult<List<Schedule>> {
        val response = remoteDataSource.getActiveSchedules(professionalId)
        return when {
            response.isSuccessful && response.data != null -> {
                DefaultResult.Success(response.data.map { it.toDomain() })
            }
            else -> {
                DefaultResult.Error(null, response.message)
            }
        }
    }
}
