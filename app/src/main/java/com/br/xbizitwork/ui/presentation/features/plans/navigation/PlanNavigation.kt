package com.br.xbizitwork.ui.presentation.features.plans.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.plans.screen.PlanScreen
import com.br.xbizitwork.ui.presentation.features.plans.viewmodel.PlanViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.planScreen(
    onNavigateUp: () -> Unit,
    onNavigateToLogin: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {}
) {
    composable<MenuScreens.PlanScreen> {
        val viewModel: PlanViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        PlanScreen(
            uiState = uiState,
            appSideEffectFlow = viewModel.sideEffectChannel,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateUp,
            onNavigateToLogin = onNavigateToLogin,
            onNavigateToSignUp = onNavigateToSignUp
        )
    }
}

fun NavController.navigateToPlanScreen() {
    navigate(MenuScreens.PlanScreen) {
        launchSingleTop = true
    }
}
