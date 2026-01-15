package com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.events

/**
 * Events for professional search by skill
 * Following the same pattern as SkillsEvent
 */
sealed class SearchProfessionalBySkillEvent {
    data class OnSearchQueryChanged(val query: String) : SearchProfessionalBySkillEvent()
    data object OnSearchClicked : SearchProfessionalBySkillEvent()
    data object OnClearSearch : SearchProfessionalBySkillEvent()
}
