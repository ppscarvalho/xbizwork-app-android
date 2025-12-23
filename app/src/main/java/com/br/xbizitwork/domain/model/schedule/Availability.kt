package com.br.xbizitwork.domain.model.schedule

import java.time.LocalDateTime

/**
 * Representa a disponibilidade do profissional para um período específico
 *
 * @property id Identificador único
 * @property professionalId ID do profissional
 * @property workingHours Lista de horários de trabalho por dia da semana
 * @property effectiveFrom Data de início da vigência desta disponibilidade
 * @property effectiveUntil Data de término da vigência (null = indeterminado)
 * @property isActive Se esta disponibilidade está ativa
 * @property createdAt Data de criação
 * @property updatedAt Data da última atualização
 */
data class Availability(
    val id: String,
    val professionalId: String,
    val workingHours: List<WorkingHours>,
    val effectiveFrom: LocalDateTime,
    val effectiveUntil: LocalDateTime? = null,
    val isActive: Boolean = true,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    /**
     * Retorna os dias da semana que o profissional trabalha
     */
    fun getActiveDays(): List<DayOfWeek> {
        return workingHours.filter { it.isActive }.map { it.dayOfWeek }
    }
    
    /**
     * Retorna o WorkingHours para um dia específico
     */
    fun getWorkingHoursForDay(day: DayOfWeek): WorkingHours? {
        return workingHours.find { it.dayOfWeek == day && it.isActive }
    }
    
    /**
     * Verifica se está disponível em uma data específica
     */
    fun isAvailableOn(date: LocalDateTime): Boolean {
        if (!isActive) return false
        if (date < effectiveFrom) return false
        if (effectiveUntil != null && date > effectiveUntil) return false
        
        return true
    }
}
