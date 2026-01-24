package com.br.xbizitwork.ui.presentation.features.professionalprofile.events

/**
 * Events for professional profile screen
 * Following the same pattern as SearchProfessionalBySkillEvent
 */
sealed class ProfessionalProfileEvent {
    data object OnContactClick : ProfessionalProfileEvent()
}
