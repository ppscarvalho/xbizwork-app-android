package com.br.xbizitwork.domain.model.schedule

import java.time.LocalDateTime

/**
 * Representa uma agenda completa do profissional
 *
 * @property id Identificador único da agenda
 * @property professionalId ID do profissional
 * @property category Categoria do serviço (ex: "Educador Físico")
 * @property specialty Especialidade dentro da categoria (ex: "Treino para Emagrecimento")
 * @property availability Disponibilidade do profissional (horários de trabalho)
 * @property timeSlots Slots de horário específicos (agendamentos confirmados)
 * @property serviceDurationMinutes Duração padrão do serviço em minutos
 * @property isActive Se a agenda está ativa
 * @property createdAt Data de criação
 * @property updatedAt Data da última atualização
 */
data class Schedule(
    val id: String,
    val professionalId: String,
    val category: String,
    val specialty: String,
    val availability: Availability,
    val timeSlots: List<TimeSlot> = emptyList(),
    val serviceDurationMinutes: Int = 60,
    val isActive: Boolean = true,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    /**
     * Verifica se há conflito com um novo TimeSlot
     */
    fun hasConflict(newSlot: TimeSlot): Boolean {
        return timeSlots.any { it.conflictsWith(newSlot) }
    }
    
    /**
     * Retorna slots disponíveis para um dia específico
     */
    fun getAvailableSlotsForDay(day: DayOfWeek): List<TimeSlot> {
        return timeSlots.filter { 
            it.dayOfWeek == day && it.isAvailable 
        }
    }
    
    /**
     * Retorna slots ocupados para um dia específico
     */
    fun getOccupiedSlotsForDay(day: DayOfWeek): List<TimeSlot> {
        return timeSlots.filter { 
            it.dayOfWeek == day && !it.isAvailable 
        }
    }
}
