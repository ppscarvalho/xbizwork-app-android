package com.br.xbizitwork.ui.presentation.features.professionalprofile.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.features.professionalprofile.screen.ProfessionalProfileScreen
import com.br.xbizitwork.ui.presentation.features.professionalprofile.viewmodel.ProfessionalProfileViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

/**
 * Navigation extension for ProfessionalProfileScreen
 * Following the same pattern as searchProfessionalBySkillScreen
 */
fun NavGraphBuilder.professionalProfileScreen(
    onNavigateUp: () -> Unit,
    getProfessional: (Int) -> ProfessionalSearchBySkill?
) {
    composable<MenuScreens.ProfessionalProfileScreen> { backStackEntry ->
        val viewModel: ProfessionalProfileViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sideEffectFlow = viewModel.sideEffectChannel

        // Get professional ID from route using type-safe navigation
        val route = backStackEntry.toRoute<MenuScreens.ProfessionalProfileScreen>()
        val professionalId = route.professionalId
        
        // Get professional from shared state
        LaunchedEffect(professionalId) {
            getProfessional(professionalId)?.let { professional ->
                viewModel.setProfessional(professional)
            }
        }

        ProfessionalProfileScreen(
            uiState = uiState,
            sideEffectFlow = sideEffectFlow,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateUp
        )
    }
}

fun NavController.navigateToProfessionalProfileScreen(professionalId: Int) {
    navigate(MenuScreens.ProfessionalProfileScreen(professionalId)) {
        launchSingleTop = true
    }
}
