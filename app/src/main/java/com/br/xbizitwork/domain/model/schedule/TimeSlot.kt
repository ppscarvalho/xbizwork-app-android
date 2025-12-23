package com.br.xbizitwork.domain.model.schedule

import java.time.LocalTime

/**
 * Representa um slot de horário em um dia específico
 *
 * @property id Identificador único do slot
 * @property dayOfWeek Dia da semana
 * @property startTime Horário de início
 * @property endTime Horário de término
 * @property isAvailable Se o horário está disponível para agendamento
 */
data class TimeSlot(
    val id: String,
    val dayOfWeek: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val isAvailable: Boolean = true
) {
    /**
     * Verifica se há conflito com outro TimeSlot
     */
    fun conflictsWith(other: TimeSlot): Boolean {
        if (this.dayOfWeek != other.dayOfWeek) return false
        
        return !(this.endTime <= other.startTime || this.startTime >= other.endTime)
    }
    
    /**
     * Retorna a duração do slot em minutos
     */
    fun durationInMinutes(): Long {
        return java.time.Duration.between(startTime, endTime).toMinutes()
    }
}
