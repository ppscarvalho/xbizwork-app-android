package com.br.xbizitwork.ui.presentation.features.menu.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.menu.screen.MenuScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.menuScreen(
    onNavigateToHomeGraph: () -> Unit
){
    composable<MenuScreens.MenuScreen> {
        MenuScreen(
            onNavigateToHomeGraph = onNavigateToHomeGraph,
            onClickChangerPassword = {},
            onClickDateRange = {},
            onClickAssignment = {},
            onClickEvent = {},
            onClickViewModule = {}
        )
    }
}

fun NavController.navigateToMenuScreen(){
    navigate(MenuScreens.MenuScreen){
        launchSingleTop = true
    }
}
