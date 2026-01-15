package com.br.xbizitwork.ui.presentation.features.searchprofessional.events

/**
 * Eventos para a tela de busca de profissionais por habilidade
 * Seguindo o mesmo padrão do SkillsEvent
 */
sealed class SearchProfessionalBySkillEvent {
    /**
     * Evento disparado quando o termo de busca é atualizado
     */
    data class OnSearchTermChanged(val term: String) : SearchProfessionalBySkillEvent()
    
    /**
     * Evento para executar a busca
     */
    data object OnSearch : SearchProfessionalBySkillEvent()

    /**
     * Evento para limpar a busca
     */
    data object OnClearSearch : SearchProfessionalBySkillEvent()
}
