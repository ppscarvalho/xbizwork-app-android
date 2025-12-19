package com.br.xbizitwork.ui.presentation.features.schedule.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.schedule.screen.ScheduleScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

/**
 * Navegação da ScheduleScreen
 */
fun NavGraphBuilder.scheduleScreen(
    onNavigateUp: () -> Unit
) {
    composable<MenuScreens.CreateScheduleScreen> {
        ScheduleScreen(
            onNavigateUp = onNavigateUp
        )
    }
}
