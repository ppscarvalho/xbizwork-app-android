package com.br.xbizitwork.ui.presentation.features.searchprofessionals.state

import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill

/**
 * UI State para a tela de mapa com profissional em destaque
 */
data class ProfessionalMapUiState(
    val selectedProfessional: ProfessionalSearchBySkill? = null,
    val nearbyProfessionals: List<ProfessionalSearchBySkill> = emptyList(),
    val allProfessionals: List<ProfessionalSearchBySkill> = emptyList(),
    val radiusKm: Double = 10.0,
    val isLoading: Boolean = false,
    val isLoadingMap: Boolean = true,  // Indica que o mapa está sendo carregado
    val errorMessage: String? = null
)
