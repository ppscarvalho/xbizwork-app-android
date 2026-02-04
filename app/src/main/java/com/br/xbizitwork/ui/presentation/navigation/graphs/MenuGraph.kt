package com.br.xbizitwork.ui.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.navigation.changePasswordScreen
import com.br.xbizitwork.ui.presentation.features.faq.navigation.faqScreen
import com.br.xbizitwork.ui.presentation.features.menu.navigation.menuScreen
import com.br.xbizitwork.ui.presentation.features.newschedule.create.navigation.createDefaultScheduleScreen
import com.br.xbizitwork.ui.presentation.features.newschedule.success.navigation.scheduleSuccessScreen
import com.br.xbizitwork.ui.presentation.features.plans.navigation.planScreen
import com.br.xbizitwork.ui.presentation.features.professionalprofile.navigation.professionalProfileScreen
import com.br.xbizitwork.ui.presentation.features.profile.navigation.editProfileScreen
import com.br.xbizitwork.ui.presentation.features.schedule.agenda.navigation.professionalAgendaScreen
import com.br.xbizitwork.ui.presentation.features.schedule.create.navigation.createScheduleScreen
import com.br.xbizitwork.ui.presentation.features.schedule.list.navigation.listSchedulesScreen
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.navigation.professionalMapScreen
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.navigation.searchProfessionalBySkillScreen
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
    onNavigateToChangePasswordScreen: () -> Unit,
    onNavigateToPlanScreen: () -> Unit,
    onNavigateToHomeGraph:() -> Unit,
    onNavigateToProfessionalProfile: (Int) -> Unit,
    onNavigateToProfessionalMap: (Int) -> Unit,
    setSelectedProfessional: (ProfessionalSearchBySkill) -> Unit,
    setAllProfessionals: (List<ProfessionalSearchBySkill>) -> Unit,
    getSelectedProfessional: () -> ProfessionalSearchBySkill?,
    getAllProfessionals: () -> List<ProfessionalSearchBySkill>,
    getProfessional: (Int) -> ProfessionalSearchBySkill?,
){
    navigation<Graphs.MenuGraphs>(startDestination = MenuScreens.MenuScreen) {
        menuScreen(
            onNavigateToHomeGraph = onNavigateUp,
            onNavigateToEditProfile = onNavigateToEditProfile,
            onNavigateToListSchedulesScreen = onNavigateToListSchedulesScreen,
            onNavigateToProfessionalAgendaScreen = onNavigateToProfessionalAgendaScreen,
            onNavigateToChangePasswordScreen = onNavigateToChangePasswordScreen,
            onNavigateToCreateSkills = onNavigateToCreateSkills,
            onNavigateToPlanScreen = onNavigateToPlanScreen
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
            onNavigateUp = onNavigateUp,
            onNavigateToProfessionalProfile = { professionalId ->
                onNavigateToProfessionalProfile(professionalId)
            },
            onNavigateToProfessionalMap = { professionalId ->
                onNavigateToProfessionalMap(professionalId)
            },
            setSelectedProfessional = setSelectedProfessional,
            setAllProfessionals = setAllProfessionals
        )

        professionalProfileScreen(
            onNavigateUp = onNavigateUp,
            getProfessional = getProfessional
        )

        professionalMapScreen(
            onNavigateUp = onNavigateUp,
            getSelectedProfessional = getSelectedProfessional,
            getAllProfessionals = getAllProfessionals,
            onNavigateToProfessionalProfile = { professionalId ->
                onNavigateToProfessionalProfile(professionalId)
            },
            setSelectedProfessional = setSelectedProfessional
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

        // Plan Screen
        planScreen(
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToMenuGraph(
    navOptions: NavOptions? = null
){
    navigate(Graphs.MenuGraphs, navOptions)
}
