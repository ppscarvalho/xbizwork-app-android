package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.schedule.Availability
import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.model.schedule.TimeSlot
import java.time.LocalDate

/**
 * Repository para gerenciamento de agendas do profissional
 */
interface ScheduleRepository {
    
    /**
     * Cria uma nova agenda para o profissional (formato antigo)
     */
    suspend fun createSchedule(schedule: Schedule): DefaultResult<Schedule>
    
    /**
     * Cria uma nova agenda usando o request direto (novo formato da API)
     */
    suspend fun createScheduleFromRequest(request: com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest): DefaultResult<com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse>
    
    /**
     * Obtém todas as agendas do profissional
     */
    suspend fun getProfessionalSchedules(professionalId: String): DefaultResult<List<Schedule>>
    
    /**
     * Obtém uma agenda específica por ID
     */
    suspend fun getScheduleById(scheduleId: String): DefaultResult<Schedule>
    
    /**
     * Atualiza uma agenda existente
     */
    suspend fun updateSchedule(schedule: Schedule): DefaultResult<Schedule>
    
    /**
     * Deleta uma agenda
     */
    suspend fun deleteSchedule(scheduleId: String): DefaultResult<Unit>
    
    /**
     * Atualiza a disponibilidade de uma agenda
     */
    suspend fun updateAvailability(
        scheduleId: String,
        availability: Availability
    ): DefaultResult<Schedule>
    
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
}
