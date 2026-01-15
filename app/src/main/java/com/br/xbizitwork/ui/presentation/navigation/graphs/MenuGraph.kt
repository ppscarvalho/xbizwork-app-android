package com.br.xbizitwork.ui.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.navigation.changePasswordScreen
import com.br.xbizitwork.ui.presentation.features.menu.navigation.menuScreen
import com.br.xbizitwork.ui.presentation.features.newschedule.create.navigation.createDefaultScheduleScreen
import com.br.xbizitwork.ui.presentation.features.newschedule.success.navigation.scheduleSuccessScreen
import com.br.xbizitwork.ui.presentation.features.profile.navigation.editProfileScreen
import com.br.xbizitwork.ui.presentation.features.schedule.agenda.navigation.professionalAgendaScreen
import com.br.xbizitwork.ui.presentation.features.schedule.create.navigation.createScheduleScreen
import com.br.xbizitwork.ui.presentation.features.schedule.list.navigation.listSchedulesScreen
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.navigation.searchProfessionalBySkillScreen
import com.br.xbizitwork.ui.presentation.features.skills.navigation.skillsScreen
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
    onNavigateToCreateSkills: () -> Unit,
    onNavigateToCreateSchedule: () -> Unit,
    onNavigateToListSchedulesScreen: () -> Unit,
    onNavigateToProfessionalAgendaScreen: () -> Unit,
    onNavigateChangePasswordScreen: () -> Unit,
    onNavigateToHomeGraph:() -> Unit
){
    navigation<Graphs.MenuGraphs>(startDestination = MenuScreens.MenuScreen) {
        menuScreen(
            onNavigateToHomeGraph = onNavigateUp,
            onNavigateToEditProfile = onNavigateToEditProfile,
            onNavigateToListSchedulesScreen = onNavigateToListSchedulesScreen,
            onNavigateToProfessionalAgendaScreen = onNavigateToProfessionalAgendaScreen,
            onNavigateChangePasswordScreen = onNavigateChangePasswordScreen,
            onNavigateToCreateSkills = onNavigateToCreateSkills
        )
        editProfileScreen(
            onNavigateBack = onNavigateUp,
            onNavigateToHomeGraph = onNavigateToHomeGraph
        )
        skillsScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToHomeGraph = onNavigateToHomeGraph
        )

        searchProfessionalBySkillScreen(
            onNavigateUp = onNavigateUp
        )

        createDefaultScheduleScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToScheduleSuccess = onNavigateToCreateSkills
        )

        scheduleSuccessScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToListSchedulesScreen = onNavigateToListSchedulesScreen
        )
        // Schedule Screens
        createScheduleScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToListSchedulesScreen = onNavigateToListSchedulesScreen
        )

        listSchedulesScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToCreateSchedule = onNavigateToCreateSchedule,
            //onNavigateToLogin = onNavigateToLogin
        )
        changePasswordScreen(
            onNavigateBack = onNavigateUp
        )

        professionalAgendaScreen(onNavigateUp = onNavigateUp)
    }
}

fun NavController.navigateToMenuGraph(
    navOptions: NavOptions? = null
){
    navigate(Graphs.MenuGraphs, navOptions)
}
