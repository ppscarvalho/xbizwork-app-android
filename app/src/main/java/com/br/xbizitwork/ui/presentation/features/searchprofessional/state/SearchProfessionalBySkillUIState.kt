package com.br.xbizitwork.ui.presentation.features.searchprofessional.state

import com.br.xbizitwork.domain.model.skills.ProfessionalSearchResult

/**
 * Estado de UI para a tela de busca de profissionais por habilidade
 * Seguindo o mesmo padrão do SkillUiState
 */
data class SearchProfessionalBySkillUIState(
    val searchTerm: String = "",
    val professionals: List<ProfessionalSearchResult> = emptyList(),
    
    // Estados da UI
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val errorMessage: String? = null,
)
