package com.br.xbizitwork.ui.presentation.features.schedule.list.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.schedule.list.screen.ViewSchedulesScreen
import com.br.xbizitwork.ui.presentation.features.schedule.list.viewmodel.ViewSchedulesViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.viewSchedulesScreen(
    onNavigateUp: () -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable<MenuScreens.ViewSchedulesScreen> {
        val viewModel: ViewSchedulesViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        ViewSchedulesScreen(
            uiState = uiState.value,
            sideEffectFlow = viewModel.sideEffectChannel,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateUp,
            onNavigateToCreate = onNavigateToCreate,
            onNavigateToLogin = onNavigateToLogin
        )
    }
}

fun NavController.navigateToViewSchedules() {
    navigate(MenuScreens.ViewSchedulesScreen)
}
