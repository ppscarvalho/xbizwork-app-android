package com.br.xbizitwork.domain.model.schedule

import java.time.LocalTime

/**
 * Representa o horário de trabalho em um dia específico
 *
 * @property dayOfWeek Dia da semana
 * @property startTime Horário de início do trabalho
 * @property endTime Horário de término do trabalho
 * @property isActive Se este dia está ativo na agenda
 * @property breakStartTime Horário de início do intervalo (opcional)
 * @property breakEndTime Horário de término do intervalo (opcional)
 */
data class WorkingHours(
    val dayOfWeek: DayOfWeek,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val isActive: Boolean = true,
    val breakStartTime: LocalTime? = null,
    val breakEndTime: LocalTime? = null
) {
    /**
     * Valida se os horários são consistentes
     */
    fun isValid(): Boolean {
        if (startTime >= endTime) return false
        
        if (breakStartTime != null && breakEndTime != null) {
            if (breakStartTime >= breakEndTime) return false
            if (breakStartTime < startTime || breakEndTime > endTime) return false
        }
        
        return true
    }
    
    /**
     * Retorna a duração total de trabalho em minutos (excluindo intervalo)
     */
    fun totalWorkingMinutes(): Long {
        val totalMinutes = java.time.Duration.between(startTime, endTime).toMinutes()
        
        if (breakStartTime != null && breakEndTime != null) {
            val breakMinutes = java.time.Duration.between(breakStartTime, breakEndTime).toMinutes()
            return totalMinutes - breakMinutes
        }
        
        return totalMinutes
    }
}
