package com.br.xbizitwork.ui.presentation.features.searchprofessionals.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.screen.SearchProfessionalsScreen
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.viewmodel.SearchProfessionalsViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

/**
 * Navigation extension for SearchProfessionalBySkillScreen
 * Following the same pattern as skillsScreen
 */
fun NavGraphBuilder.searchProfessionalBySkillScreen(
    onNavigateUp: () -> Unit,
    onNavigateToProfessionalProfile: (Int) -> Unit,
    setSelectedProfessional: (ProfessionalSearchBySkill) -> Unit
) {
    composable<MenuScreens.SearchProfessionalBySkillScreen> {
        val viewModel: SearchProfessionalsViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        SearchProfessionalsScreen(
            uiState = uiState,
            appSideEffectFlow = viewModel.sideEffectChannel,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateUp,
            onProfessionalSelected = { professional ->
                if (viewModel.onProfessionalSelected(professional)) {
                    setSelectedProfessional(professional)
                    onNavigateToProfessionalProfile(professional.id)
                }
            }
        )
    }
}

fun NavController.navigateToSearchProfessionalBySkillScreen() {
    navigate(MenuScreens.SearchProfessionalBySkillScreen) {
        launchSingleTop = true
    }
}
