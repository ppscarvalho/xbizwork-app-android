package com.br.xbizitwork.ui.presentation.features.faq.events

/**
 * Events for FAQ feature
 * Following the same pattern as SkillsEvent
 */
sealed class FaqEvent {
    data object OnLoadFaqSections : FaqEvent()
    data class OnToggleSection(val sectionId: Int) : FaqEvent()
    data class OnToggleQuestion(val sectionId: Int, val questionId: Int) : FaqEvent()
}
