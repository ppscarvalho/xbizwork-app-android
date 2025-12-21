package com.br.xbizitwork.ui.presentation.features.schedule.list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.schedule.list.screen.ViewSchedulesScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.viewSchedulesScreen(
    onNavigateUp: () -> Unit,
    onNavigateToCreate: () -> Unit
) {
    composable<MenuScreens.ViewSchedulesScreen> {
        ViewSchedulesScreen(
            onNavigateBack = onNavigateUp,
            onNavigateToCreate = onNavigateToCreate
        )
    }
}

fun NavController.navigateToViewSchedules() {
    navigate(MenuScreens.ViewSchedulesScreen)
}
