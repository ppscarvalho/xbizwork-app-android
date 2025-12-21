package com.br.xbizitwork.ui.presentation.components.schedule

/**
 * Representa um slot de horário na agenda do profissional
 *
 * @property id Identificador único do slot
 * @property dayOfWeek Dia da semana (ex: "Segunda-feira", "Terça-feira")
 * @property startTime Horário de início no formato "HH:mm" (ex: "08:00")
 * @property endTime Horário de término no formato "HH:mm" (ex: "10:00")
 */
data class TimeSlotItem(
    val id: String = "",
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String
)
