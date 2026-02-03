package com.br.xbizitwork.ui.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.navigation.navigateToChangePasswordScreen
import com.br.xbizitwork.ui.presentation.features.faq.navigation.faqScreen
import com.br.xbizitwork.ui.presentation.features.faq.navigation.navigateToFaqScreen
import com.br.xbizitwork.ui.presentation.features.home.navigation.homeScreen
import com.br.xbizitwork.ui.presentation.features.professionalprofile.navigation.navigateToProfessionalProfileScreen
import com.br.xbizitwork.ui.presentation.features.profile.navigation.navigateToEditProfileScreen
import com.br.xbizitwork.ui.presentation.features.schedule.agenda.navigation.navigateToProfessionalAgenda
import com.br.xbizitwork.ui.presentation.features.schedule.create.navigation.navigateToCreateSchedule
import com.br.xbizitwork.ui.presentation.features.schedule.list.navigation.navigateToViewSchedules
import com.br.xbizitwork.ui.presentation.features.schedule.search.navigation.searchScheduleScreen
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.navigation.navigateToProfessionalMapScreen
import com.br.xbizitwork.ui.presentation.features.skills.navigation.navigateToCreateSkillsScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs
import com.br.xbizitwork.ui.presentation.navigation.screens.HomeScreens

fun NavGraphBuilder.homeGraph(
    onNavigateUp: () -> Unit,
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigationToFaqScreen: () -> Unit,
    onNavigateToMenuGraph: () -> Unit,
    onNavigateProfileScreen: () -> Unit,
    onNavigationToSearchProfessionalSkillScreen: () -> Unit,
    navController: NavController,
    setSelectedProfessional: (ProfessionalSearchBySkill) -> Unit,
    getSelectedProfessional: (Int) -> ProfessionalSearchBySkill?,
    getSelectedProfessionalDirect: () -> ProfessionalSearchBySkill?,
    getProfessionalById: (Int) -> ProfessionalSearchBySkill?,
    setAllProfessionals: (List<ProfessionalSearchBySkill>) -> Unit,
    getAllProfessionals: () -> List<ProfessionalSearchBySkill>
){
    navigation<Graphs.HomeGraphs>(startDestination = HomeScreens.HomeScreen) {
        homeScreen(
            onNavigateToSignInScreen = onNavigateToSignInScreen,
            onNavigateToProfileScreen = onNavigateToProfileScreen,
            onNavigateToSearchScreen = onNavigateToSearchScreen,
            onNavigationToFaqScreen = onNavigationToFaqScreen,
            onNavigateToMenuScreen = onNavigateToMenuGraph,
            onNavigateProfileClick = onNavigateProfileScreen,
            onNavigationToSearchProfessionalSkillScreen = onNavigationToSearchProfessionalSkillScreen
        )
        searchScheduleScreen(
            onNavigateBack = onNavigateUp,
            onNavigateToScheduleDetail = {}
        )

        faqScreen(
            onNavigateBack = onNavigateUp,
        )

        // Menu é um nested graph com suas próprias screens
        menuGraph(
            onNavigateUp = onNavigateUp,
            onNavigateToEditProfile = {
                navController.navigateToEditProfileScreen()
            },
            onNavigateToCreateSkills = {
              navController.navigateToCreateSkillsScreen()
            },
            onNavigateToCreateSchedule = {
                navController.navigateToCreateSchedule()
            },
            onNavigateToListSchedulesScreen = {
                navController.navigateToViewSchedules()
            },
            onNavigateToProfessionalAgendaScreen = {
                navController.navigateToProfessionalAgenda()
            },
            onNavigateToChangePasswordScreen = {
                navController.navigateToChangePasswordScreen()
            },
            onNavigateToHomeGraph = {
                navController.navigationToHomeGraph()
            },
            onNavigateToProfessionalProfile = { professionalId ->
                navController.navigateToProfessionalProfileScreen(professionalId)
            },
            onNavigateToProfessionalMap = { professionalId ->
                navController.navigateToProfessionalMapScreen(professionalId)
            },
            setSelectedProfessional = setSelectedProfessional,
            setAllProfessionals = setAllProfessionals,
            getSelectedProfessional = getSelectedProfessionalDirect,
            getAllProfessionals = getAllProfessionals,
            getProfessional = getProfessionalById,
        )
    }
}

fun NavController.navigationToHomeGraph(
    navOptions: NavOptions? = null
){
    navigate(Graphs.HomeGraphs, navOptions)
}