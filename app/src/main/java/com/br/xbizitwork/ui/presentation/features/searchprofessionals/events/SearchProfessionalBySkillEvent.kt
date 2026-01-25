package com.br.xbizitwork.ui.presentation.features.searchprofessionals.events

/**
 * Events for professional search by skill
 * Following the same pattern as SearchSchedulesEvent
 */
sealed class SearchProfessionalBySkillEvent {
    data object OnRefresh : SearchProfessionalBySkillEvent()
}
