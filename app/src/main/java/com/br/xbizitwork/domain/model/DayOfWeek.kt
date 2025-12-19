package com.br.xbizitwork.domain.model

/**
 * Enum para dias da semana
 */
enum class DayOfWeek(val id: Int, val displayName: String) {
    SUNDAY(1, "Domingo"),
    MONDAY(2, "Segunda-feira"),
    TUESDAY(3, "Terça-feira"),
    WEDNESDAY(4, "Quarta-feira"),
    THURSDAY(5, "Quinta-feira"),
    FRIDAY(6, "Sexta-feira"),
    SATURDAY(7, "Sábado");

    companion object {
        fun fromId(id: Int): DayOfWeek? = entries.find { it.id == id }
    }
}
