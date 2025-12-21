package com.br.xbizitwork.ui.presentation.features.schedule.agenda.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.schedule.agenda.screen.ProfessionalAgendaScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.professionalAgendaScreen(
    onNavigateUp: () -> Unit
) {
    composable<MenuScreens.ProfessionalAgendaScreen> {
        ProfessionalAgendaScreen(
            onNavigateBack = onNavigateUp
        )
    }
}

fun NavController.navigateToProfessionalAgenda() {
    navigate(MenuScreens.ProfessionalAgendaScreen)
}
