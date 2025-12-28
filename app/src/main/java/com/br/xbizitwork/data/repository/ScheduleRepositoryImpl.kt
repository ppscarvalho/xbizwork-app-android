package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.schedule.datasource.ScheduleRemoteDataSource
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.mappers.toDomain
import com.br.xbizitwork.data.remote.schedule.mappers.toUpdateRequest
import com.br.xbizitwork.domain.model.schedule.Availability
import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.model.schedule.TimeSlot
import com.br.xbizitwork.domain.repository.ScheduleRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação do ScheduleRepository
 *
 * Padrão estabelecido:
 * - CREATE/UPDATE/DELETE retorna ApiResultModel (isSuccessful, message)
 * - GET/LIST retorna objetos de domínio
 */
class ScheduleRepositoryImpl @Inject constructor(
    private val remoteDataSource: ScheduleRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ScheduleRepository {

    override suspend fun createSchedule(schedule: Schedule): DefaultResult<ApiResultModel> {
        // NOTA: Método antigo mantido para compatibilidade, mas não funciona com novo formato da API
        // Use createScheduleFromRequest() ao invés deste
        throw UnsupportedOperationException("Use createScheduleFromRequest() para criar agendas com o novo formato da API")
    }

    override suspend fun createScheduleFromRequest(request: CreateScheduleRequest): DefaultResult<ApiResultModel> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.createSchedule(request)
            when {
                response.isSuccessful ->
                    DefaultResult.Success(ApiResultModel(response.isSuccessful, response.message))
                else ->
                    DefaultResult.Error(message = response.message)
            }
        }

    override suspend fun getProfessionalSchedules(professionalId: String): DefaultResult<List<Schedule>> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.getProfessionalSchedules(professionalId)
            when {
                response.isSuccessful && response.data != null -> {
                    DefaultResult.Success(response.data.map { it.toDomain() })
                }
                else -> {
                    DefaultResult.Error(null, response.message)
                }
            }
        }

    override suspend fun getScheduleById(scheduleId: String): DefaultResult<Schedule> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.getScheduleById(scheduleId)
            when {
                response.isSuccessful && response.data != null -> {
                    DefaultResult.Success(response.data.toDomain())
                }
                else -> {
                    DefaultResult.Error(null, response.message)
                }
            }
        }

    override suspend fun updateSchedule(schedule: Schedule): DefaultResult<ApiResultModel> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.updateSchedule(schedule.id, schedule.toUpdateRequest())
            when {
                response.isSuccessful -> {
                    DefaultResult.Success(ApiResultModel(response.isSuccessful, response.message))
                }
                else -> {
                    DefaultResult.Error(null, response.message)
                }
            }
        }

    override suspend fun deleteSchedule(scheduleId: String): DefaultResult<ApiResultModel> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.deleteSchedule(scheduleId)
            when {
                response.isSuccessful -> {
                    DefaultResult.Success(ApiResultModel(response.isSuccessful, response.message))
                }
                else -> {
                    DefaultResult.Error(null, response.message)
                }
            }
        }

    override suspend fun updateAvailability(
        scheduleId: String,
        availability: Availability
    ): DefaultResult<ApiResultModel> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.updateAvailability(
                scheduleId,
                availability.toUpdateRequest()
            )
            when {
                response.isSuccessful -> {
                    DefaultResult.Success(ApiResultModel(response.isSuccessful, response.message))
                }
                else -> {
                    DefaultResult.Error(null, response.message)
                }
            }
        }

    override suspend fun getAvailableTimeSlots(
        scheduleId: String, date: java.time.LocalDate,
        dayOfWeek: DayOfWeek
    ): DefaultResult<List<TimeSlot>> =
        withContext(coroutineDispatcherProvider.io()) {
            val dateStr = date.toString()

            val response = remoteDataSource.getAvailableTimeSlots(
                scheduleId,
                dateStr,
                dayOfWeek.name
            )
            when {
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
    ): DefaultResult<TimeSlot> =
        withContext(coroutineDispatcherProvider.io()) {
            DefaultResult.Error(null, "Funcionalidade não implementada")
        }

    override suspend fun releaseTimeSlot(
        scheduleId: String,
        timeSlotId: String
    ): DefaultResult<TimeSlot> =
        withContext(coroutineDispatcherProvider.io()) {
            DefaultResult.Error(null, "Funcionalidade não implementada")
        }

    override suspend fun getActiveSchedules(professionalId: String): DefaultResult<List<Schedule>> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.getActiveSchedules(professionalId)
            when {
                response.isSuccessful && response.data != null -> {
                    DefaultResult.Success(response.data.map { it.toDomain() })
                }
                else -> {
                    DefaultResult.Error(null, response.message)
                }
            }
        }

    override suspend fun validateSchedule(request: CreateScheduleRequest): DefaultResult<ApiResultModel> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.validateSchedule(request)
            when {
                response.isSuccessful ->
                    DefaultResult.Success(ApiResultModel(response.isSuccessful, response.message))
                else ->
                    DefaultResult.Error(message = response.message)
            }
        }

    override suspend fun getCategoryByDescription(description: String): DefaultResult<List<Schedule>> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.getCategoryByDescription(description)
            when {
                response.isSuccessful && response.data != null -> {
                    DefaultResult.Success(response.data.map { it.toDomain() })
                }
                else -> {
                    DefaultResult.Error(null, response.message)
                }
            }
        }
}
