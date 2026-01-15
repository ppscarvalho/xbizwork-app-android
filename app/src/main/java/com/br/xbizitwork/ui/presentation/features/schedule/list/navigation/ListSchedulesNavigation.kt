package com.br.xbizitwork.ui.presentation.features.schedule.list.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.schedule.list.screen.ListSchedulesScreen
import com.br.xbizitwork.ui.presentation.features.schedule.list.viewmodel.ListSchedulesViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.listSchedulesScreen(
    onNavigateUp: () -> Unit,
    onNavigateToCreateSchedule: () -> Unit,
    //onNavigateToLogin: () -> Unit
) {
    composable<MenuScreens.ListSchedulesScreen> {
        val viewModel: ListSchedulesViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        ListSchedulesScreen(
            uiState = uiState.value,
            appSideEffectFlow = viewModel.sideEffectChannel,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateUp,
            onNavigateToCreateSchedule = onNavigateToCreateSchedule,
            //onNavigateToLogin = onNavigateToLogin
        )
    }
}

fun NavController.navigateToViewSchedules() {
    navigate(MenuScreens.ListSchedulesScreen)
}
