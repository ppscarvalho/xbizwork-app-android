package com.br.xbizitwork.ui.presentation.features.searchprofessionals.events

import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill

/**
 * Events for professional search by skill
 * Following the same pattern as SearchSchedulesEvent
 */
sealed class SearchProfessionalBySkillEvent {
    data object OnRefresh : SearchProfessionalBySkillEvent()
    data class OnProfessionalSelected(val professional: ProfessionalSearchBySkill) : SearchProfessionalBySkillEvent()
}
