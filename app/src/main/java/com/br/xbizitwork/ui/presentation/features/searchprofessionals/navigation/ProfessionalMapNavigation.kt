package com.br.xbizitwork.ui.presentation.features.searchprofessionals.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.screen.ProfessionalMapScreen
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.viewmodel.ProfessionalMapViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

/**
 * Navegação para tela de mapa com profissional em destaque
 * Suporta visualização rápida de profissionais não selecionados via BottomSheet
 */
fun NavGraphBuilder.professionalMapScreen(
    onNavigateUp: () -> Unit,
    getSelectedProfessional: () -> ProfessionalSearchBySkill?,
    getAllProfessionals: () -> List<ProfessionalSearchBySkill>,
    onNavigateToProfessionalProfile: (Int) -> Unit,
    setSelectedProfessional: (ProfessionalSearchBySkill) -> Unit
) {
    composable<MenuScreens.ProfessionalMapScreen> {
        val viewModel: ProfessionalMapViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        val selectedProfessional = getSelectedProfessional()
        val allProfessionals = getAllProfessionals()

        ProfessionalMapScreen(
            uiState = uiState,
            selectedProfessional = selectedProfessional,
            allProfessionals = allProfessionals,
            onNavigateBack = onNavigateUp,
            onInitializeMap = { professional, professionals ->
                viewModel.initializeMap(professional, professionals)
            },
            onProfessionalClick = { professional ->
                onNavigateToProfessionalProfile(professional.id)
            },
            setSelectedProfessional = setSelectedProfessional
        )
    }
}

fun NavController.navigateToProfessionalMapScreen(professionalId: Int) {
    navigate(MenuScreens.ProfessionalMapScreen(professionalId)) {
        launchSingleTop = true
    }
}
