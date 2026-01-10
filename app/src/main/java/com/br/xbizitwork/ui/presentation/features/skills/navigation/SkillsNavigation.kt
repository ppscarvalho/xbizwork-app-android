package com.br.xbizitwork.ui.presentation.features.skills.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.skills.screen.SkillsScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.skillsScreen(
    onNavigateUp: () -> Unit
){
    composable<MenuScreens.CreateSkillsScreen> {
        SkillsScreen(
            onNavigateBack = onNavigateUp
        )
    }
}

fun NavController.navigateToCreateSkillsScreen(){
    navigate(MenuScreens.CreateSkillsScreen){
        launchSingleTop = true
    }
}
