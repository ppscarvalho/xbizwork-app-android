package com.br.xbizitwork.domain.model.schedule

/**
 * Enumeração dos dias da semana
 */
enum class DayOfWeek(val displayName: String, val shortName: String) {
    MONDAY("Segunda-feira", "Seg"),
    TUESDAY("Terça-feira", "Ter"),
    WEDNESDAY("Quarta-feira", "Qua"),
    THURSDAY("Quinta-feira", "Qui"),
    FRIDAY("Sexta-feira", "Sex"),
    SATURDAY("Sábado", "Sáb"),
    SUNDAY("Domingo", "Dom");
    
    companion object {
        fun fromDisplayName(name: String): DayOfWeek? {
            return entries.find { it.displayName == name }
        }
    }
}
