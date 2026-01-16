package com.br.xbizitwork.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.br.xbizitwork.ui.presentation.features.auth.signin.navigation.navigateToSignInScreen
import com.br.xbizitwork.ui.presentation.features.auth.signup.navigation.navigateToSignUpScreen
import com.br.xbizitwork.ui.presentation.features.schedule.search.navigation.navigateToSearchScheduleScreen
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.navigation.navigateToSearchProfessionalBySkillScreen
import com.br.xbizitwork.ui.presentation.navigation.graphs.authGraph
import com.br.xbizitwork.ui.presentation.navigation.graphs.homeGraph
import com.br.xbizitwork.ui.presentation.navigation.graphs.navigationToHomeGraph
import com.br.xbizitwork.ui.presentation.navigation.graphs.navigateToMenuGraph
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs

@Composable
fun RootHost(
    startDestination: Graphs,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        authGraph(
            onNavigateToHomeGraph = {navOptions ->
                navController.navigationToHomeGraph(navOptions)
            },
            onNavigateToSignUpScreen = {
                navController.navigateToSignUpScreen()
            },
            onNavigateToSignInScreen = {
                navController.popBackStack()
            },
            onNavigateBack = {
                navController.popBackStack()
            }
        )
        homeGraph(
            onNavigateUp = {
                navController.navigateUp()
            },
            onNavigateToProfileScreen = {
                navController.navigateUp()
            },
            onNavigateToSearchScreen = {
                navController.navigateToSearchScheduleScreen()
            },
            onNavigateToUsersConnectionScreen = {},
            onNavigateToMenuGraph = {
                navController.navigateToMenuGraph()
            },
            onNavigateProfileScreen = {},
            onNavigateToSignInScreen = {
                navController.navigateToSignInScreen()
            },
            onNavigationToSearchProfessionalSkillScreen = {
                navController.navigateToSearchProfessionalBySkillScreen()
            },
            navController = navController
        )
    }
}