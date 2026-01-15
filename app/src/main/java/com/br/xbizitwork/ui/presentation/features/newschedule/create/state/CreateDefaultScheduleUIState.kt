package com.br.xbizitwork.ui.presentation.features.newschedule.create.state

data class CreateDefaultScheduleUIState(
    // Estado de controle
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,

    // Informações da agenda padrão (exibição)
    val daysDescription: String = "Segunda a Sexta",
    val startTime: String = "08:00",
    val endTime: String = "18:00"
)
