package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleValidationResult
import com.br.xbizitwork.domain.model.schedule.Availability
import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.model.schedule.TimeSlot
import java.time.LocalDate

/**
 * Repository para gerenciamento de agendas do profissional
 *
 * Padrão:
 * - CREATE/UPDATE/DELETE retorna ApiResultModel (isSuccessful, message)
 * - GET/LIST retorna objetos de domínio
 */
interface ScheduleRepository {

    /**
     * Cria uma nova agenda para o profissional (formato antigo)
     */
    suspend fun createSchedule(schedule: Schedule): DefaultResult<ApiResultModel>

    /**
     * Cria uma nova agenda usando o request direto (novo formato da API)
     * CREATE - retorna ApiResultModel
     */
    suspend fun createScheduleFromRequest(request: CreateScheduleRequest): DefaultResult<ApiResultModel>

    /**
     * Obtém todas as agendas do profissional
     * LIST - retorna List<Schedule>
     */
    suspend fun getProfessionalSchedules(professionalId: String): DefaultResult<List<Schedule>>

    /**
     * Obtém uma agenda específica por ID
     * GET - retorna Schedule
     */
    suspend fun getScheduleById(scheduleId: String): DefaultResult<Schedule>

    /**
     * Atualiza uma agenda existente
     * UPDATE - retorna ApiResultModel
     */
    suspend fun updateSchedule(schedule: Schedule): DefaultResult<ApiResultModel>

    /**
     * Deleta uma agenda
     * DELETE - retorna ApiResultModel
     */
    suspend fun deleteSchedule(scheduleId: String): DefaultResult<ApiResultModel>

    /**
     * Atualiza a disponibilidade de uma agenda
     * UPDATE - retorna ApiResultModel
     */
    suspend fun updateAvailability(
        scheduleId: String,
        availability: Availability
    ): DefaultResult<ApiResultModel>

    /**
     * Obtém slots disponíveis para um dia específico
     */
    suspend fun getAvailableTimeSlots(
        scheduleId: String,
        date: LocalDate,
        dayOfWeek: DayOfWeek
    ): DefaultResult<List<TimeSlot>>

    /**
     * Bloqueia um slot de horário (marca como indisponível)
     */
    suspend fun blockTimeSlot(
        scheduleId: String,
        timeSlot: TimeSlot
    ): DefaultResult<TimeSlot>

    /**
     * Libera um slot de horário (marca como disponível)
     */
    suspend fun releaseTimeSlot(
        scheduleId: String,
        timeSlotId: String
    ): DefaultResult<TimeSlot>

    /**
     * Obtém agendas ativas do profissional
     */
    suspend fun getActiveSchedules(professionalId: String): DefaultResult<List<Schedule>>

    /**
     * Valida uma nova agenda antes de criá-la
     * Retorna ApiResultModel com isSuccessful e message
     */
    suspend fun validateSchedule(request: CreateScheduleRequest): DefaultResult<ApiResultModel>
}