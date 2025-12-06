package com.br.xbizitwork.ui.presentation.features.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.home.screen.DefaultScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.HomeScreens

fun NavGraphBuilder.homeScreen(
    onNavigationToProfileScreen: () -> Unit,
    onNavigationToSearchScreen: () -> Unit,
    onNavigationToUsersConnectionScreen: () -> Unit,
    onNavigationToMenuScreen: () -> Unit,
    onProfileClick: () -> Unit
){
    composable<HomeScreens.HomeScreen> {
        DefaultScreen(
            onLogout = {},
            onNavigationToProfileScreen = onNavigationToProfileScreen,
            onNavigationToSearchScreen = onNavigationToSearchScreen,
            onNavigationToUsersConnectionScreen = onNavigationToUsersConnectionScreen,
            onNavigationToMenuScreen = onNavigationToMenuScreen,
            onProfileClick = onProfileClick
        )
    }
}
