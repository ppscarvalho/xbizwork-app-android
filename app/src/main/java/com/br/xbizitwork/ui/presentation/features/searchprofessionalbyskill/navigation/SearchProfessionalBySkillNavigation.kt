package com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.screen.SearchProfessionalBySkillScreen
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.viewmodel.SearchProfessionalBySkillViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

/**
 * Navigation extension for SearchProfessionalBySkillScreen
 * Following the same pattern as skillsScreen
 */
fun NavGraphBuilder.searchProfessionalBySkillScreen(
    onNavigateUp: () -> Unit
) {
    composable<MenuScreens.SearchProfessionalBySkillScreen> {
        val viewModel: SearchProfessionalBySkillViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        SearchProfessionalBySkillScreen(
            uiState = uiState,
            appSideEffectFlow = viewModel.sideEffectChannel,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateUp
        )
    }
}

fun NavController.navigateToSearchProfessionalBySkillScreen() {
    navigate(MenuScreens.SearchProfessionalBySkillScreen) {
        launchSingleTop = true
    }
}
