package com.br.xbizitwork.ui.presentation.features.schedule.create.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.schedule.create.screen.CreateScheduleScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.createScheduleScreen(
    onNavigateUp: () -> Unit
) {
    composable<MenuScreens.CreateScheduleScreen> {
        CreateScheduleScreen(
            onNavigateBack = onNavigateUp
        )
    }
}

fun NavController.navigateToCreateSchedule() {
    navigate(MenuScreens.CreateScheduleScreen)
}
