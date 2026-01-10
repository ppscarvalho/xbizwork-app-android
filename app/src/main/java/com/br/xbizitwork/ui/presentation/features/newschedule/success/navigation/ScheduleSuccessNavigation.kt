package com.br.xbizitwork.ui.presentation.features.newschedule.success.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.newschedule.success.screen.ScheduleSuccessScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.scheduleSuccessScreen(
    onNavigateUp: () -> Unit,
    onNavigateToListSchedulesScreen: () -> Unit
) {
    composable<MenuScreens.NewScheduleSuccessScreen> {
        ScheduleSuccessScreen(
            onNavigateBack = onNavigateUp,
            onNavigateToListSchedulesScreen = onNavigateToListSchedulesScreen
        )
    }
}

fun NavController.navigateToScheduleSuccess() {
    navigate(MenuScreens.NewScheduleSuccessScreen)
}