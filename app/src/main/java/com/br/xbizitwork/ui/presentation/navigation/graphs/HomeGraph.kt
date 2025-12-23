package com.br.xbizitwork.ui.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.br.xbizitwork.ui.presentation.features.home.navigation.homeScreen
import com.br.xbizitwork.ui.presentation.features.profile.navigation.editProfileScreen
import com.br.xbizitwork.ui.presentation.features.profile.navigation.navigateToEditProfileScreen
import com.br.xbizitwork.ui.presentation.features.schedule.agenda.navigation.navigateToProfessionalAgenda
import com.br.xbizitwork.ui.presentation.features.schedule.create.navigation.navigateToCreateSchedule
import com.br.xbizitwork.ui.presentation.features.schedule.list.navigation.navigateToViewSchedules
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs
import com.br.xbizitwork.ui.presentation.navigation.screens.HomeScreens

fun NavGraphBuilder.homeGraph(
    onNavigateUp: () -> Unit,
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToUsersConnectionScreen: () -> Unit,
    onNavigateToMenuGraph: () -> Unit,
    onNavigateProfileClick: () -> Unit,
    navController: NavController
){
    navigation<Graphs.HomeGraphs>(startDestination = HomeScreens.HomeScreen) {
        homeScreen(
            onNavigateToSignInScreen = onNavigateToSignInScreen,
            onNavigateToProfileScreen = onNavigateToProfileScreen,
            onNavigateToSearchScreen = onNavigateToSearchScreen,
            onNavigateToUsersConnectionScreen = onNavigateToUsersConnectionScreen,
            onNavigateToMenuScreen = onNavigateToMenuGraph,
            onNavigateProfileClick = onNavigateProfileClick
        )

        editProfileScreen(
            onNavigateBack = onNavigateUp,
            onNavigateToLogin = onNavigateToSignInScreen
        )
        
        // Menu é um nested graph com suas próprias screens
        menuGraph(
            onNavigateUp = onNavigateUp,
            onNavigateToEditProfile = {
                navController.navigateToEditProfileScreen()
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
            onNavigateToLogin = onNavigateToSignInScreen
        )
    }
}

fun NavController.navigationToHomeGraph(
    navOptions: NavOptions? = null
){
    navigate(Graphs.HomeGraphs, navOptions)
}