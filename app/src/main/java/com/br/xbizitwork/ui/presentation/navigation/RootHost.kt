package com.br.xbizitwork.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signin.navigation.navigateToSignInScreen
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.navigation.navigateToSignUpScreen
import com.br.xbizitwork.ui.presentation.navigation.graphs.authGraph
import com.br.xbizitwork.ui.presentation.navigation.graphs.homeGraph
import com.br.xbizitwork.ui.presentation.navigation.screens.AuthScreens
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
            onNavigateToHomeGraph = {
                navController.navigate(Graphs.HomeGraph)
            },
            onNavigateToSignUpScreen = {
                navController.navigateToSignUpScreen()
            },
            onNavigateToSignInScreen = {
                navController.navigateToSignInScreen()
            }
        )
        homeGraph(
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }
}