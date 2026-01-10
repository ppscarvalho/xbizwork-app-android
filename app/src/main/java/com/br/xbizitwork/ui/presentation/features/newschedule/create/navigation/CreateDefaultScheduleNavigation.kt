package com.br.xbizitwork.ui.presentation.features.newschedule.create.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.newschedule.create.screen.CreateDefaultScheduleScreen
import com.br.xbizitwork.ui.presentation.features.newschedule.create.viewmodel.CreateDefaultScheduleViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.createDefaultScheduleScreen(
    onNavigateUp: () -> Unit,
    onNavigateToScheduleSuccess: () -> Unit
) {
    composable<MenuScreens.CreateDefaultScheduleScreen> {

        val viewModel: CreateDefaultScheduleViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sideEffect = viewModel.sideEffectChannel

        CreateDefaultScheduleScreen(
            uiState = uiState,
            appSideEffectFlow = sideEffect,
            onNavigateBack = onNavigateUp,
            onEvent = viewModel::onEvent,
            onNavigateToSuccessScreen = onNavigateToScheduleSuccess,
        )
    }
}

fun NavController.navigateToCreateDefaultSchedule() {
    navigate(MenuScreens.CreateDefaultScheduleScreen)
}