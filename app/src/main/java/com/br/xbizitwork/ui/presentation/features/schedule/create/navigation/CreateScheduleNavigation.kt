package com.br.xbizitwork.ui.presentation.features.schedule.create.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.schedule.create.screen.CreateScheduleScreen
import com.br.xbizitwork.ui.presentation.features.schedule.create.viewmodel.CreateScheduleViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.createScheduleScreen(
    onNavigateUp: () -> Unit,
    onNavigateToViewSchedules: () -> Unit
) {
    composable<MenuScreens.CreateScheduleScreen> {
        val viewModel: CreateScheduleViewModel = hiltViewModel()
        val uiState = viewModel.uiState.collectAsStateWithLifecycle()

        CreateScheduleScreen(
            uiState = uiState.value,
            appSideEffectFlow = viewModel.sideEffectChannel,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateUp,
            onNavigateToViewSchedules = onNavigateToViewSchedules
        )
    }
}

fun NavController.navigateToCreateSchedule() {
    navigate(MenuScreens.CreateScheduleScreen)
}
