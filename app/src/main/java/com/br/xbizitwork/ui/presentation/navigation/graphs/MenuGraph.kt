package com.br.xbizitwork.ui.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.br.xbizitwork.ui.presentation.features.menu.navigation.menuScreen
import com.br.xbizitwork.ui.presentation.features.schedule.agenda.navigation.professionalAgendaScreen
import com.br.xbizitwork.ui.presentation.features.schedule.create.navigation.createScheduleScreen
import com.br.xbizitwork.ui.presentation.features.schedule.list.navigation.viewSchedulesScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

/**
 * MenuGraph é um nested navigation graph dentro de HomeGraphs
 * 
 * Contém:
 * - MenuScreen (start destination)
 * - FinancialScreen
 * - CreateScheduleScreen
 * - ViewSchedulesScreen
 * - ProfessionalAgendaScreen
 * - E outros submenus
 */
fun NavGraphBuilder.menuGraph(
    onNavigateUp: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    onNavigateToCreateSchedule: () -> Unit,
    onNavigateToViewSchedulesScreen: () -> Unit,
    onNavigateToProfessionalAgendaScreen: () -> Unit,
    onNavigateToLogin: () -> Unit
){
    navigation<Graphs.MenuGraphs>(startDestination = MenuScreens.MenuScreen) {
        menuScreen(
            onNavigateToHomeGraph = onNavigateUp,
            onNavigateToEditProfile = onNavigateToEditProfile,
            onNavigateToViewSchedulesScreen = onNavigateToViewSchedulesScreen,
            onNavigateToProfessionalAgendaScreen = onNavigateToProfessionalAgendaScreen
        )
        
        // Schedule Screens
        createScheduleScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToViewSchedules = onNavigateToViewSchedulesScreen
        )

        viewSchedulesScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToCreate = onNavigateToCreateSchedule,
            onNavigateToLogin = onNavigateToLogin
        )
        
        professionalAgendaScreen(onNavigateUp = onNavigateUp)
    }
}

fun NavController.navigateToMenuGraph(
    navOptions: NavOptions? = null
){
    navigate(Graphs.MenuGraphs, navOptions)
}
