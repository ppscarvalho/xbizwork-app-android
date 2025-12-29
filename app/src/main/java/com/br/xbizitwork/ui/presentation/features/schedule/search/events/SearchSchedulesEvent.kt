package com.br.xbizitwork.ui.presentation.features.schedule.search.events

import com.br.xbizitwork.ui.presentation.features.schedule.list.events.ListSchedulesEvent

sealed class SearchSchedulesEvent {
    data object OnRefresh : SearchSchedulesEvent()
    data class OnScheduleClick(val scheduleId: String) : SearchSchedulesEvent()
    data class OnViewSchedule(val scheduleId: String) : SearchSchedulesEvent()
    data object OnObserverSearch : SearchSchedulesEvent()
}