package com.br.xbizitwork.ui.presentation.features.faq.events

/**
 * Eventos da tela de FAQ
 * Segue o padrão do SkillsEvent
 */
sealed class FaqEvent {
    data object OnLoadFaq : FaqEvent()
    data class OnToggleSection(val sectionId: Int) : FaqEvent()
    data class OnToggleQuestion(val sectionId: Int, val questionId: Int) : FaqEvent()
}
