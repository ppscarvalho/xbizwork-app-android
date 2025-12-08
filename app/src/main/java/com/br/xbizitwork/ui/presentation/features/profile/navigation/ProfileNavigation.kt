package com.br.xbizitwork.ui.presentation.features.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.profile.screen.ProfileScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.HomeScreens

fun NavGraphBuilder.profileScreen(
    onNavigateToHomeGraph: () -> Unit,
){
    composable<HomeScreens.ProfileScreen> {
        ProfileScreen(
            onNavigateToHomeGraph = onNavigateToHomeGraph
        )
    }
}

fun NavController.navigateToProfileScreen(){
    navigate(HomeScreens.ProfileScreen){
        popUpTo<HomeScreens.HomeScreen> {
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    }
}
