package com.br.xbizitwork.ui

import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs

data class MainUiState(
    val startDestination: Graphs = Graphs.AuthGraphs,
    val selectedProfessional: ProfessionalSearchBySkill? = null
)
