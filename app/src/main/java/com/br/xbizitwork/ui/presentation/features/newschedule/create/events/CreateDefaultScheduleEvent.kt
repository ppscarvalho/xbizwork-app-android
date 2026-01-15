package com.br.xbizitwork.ui.presentation.features.newschedule.create.events

sealed class CreateDefaultScheduleEvent {
    data object OnCreateDefaultSchedule : CreateDefaultScheduleEvent()
}