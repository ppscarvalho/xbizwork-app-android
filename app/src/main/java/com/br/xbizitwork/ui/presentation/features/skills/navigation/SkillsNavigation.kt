package com.br.xbizitwork.ui.presentation.features.skills.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.menu.viewmodel.MenuViewModel
import com.br.xbizitwork.ui.presentation.features.skills.screen.SkillsScreen
import com.br.xbizitwork.ui.presentation.features.skills.viewmodel.SkillsViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.skillsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToHomeGraph: () -> Unit,
){
    composable<MenuScreens.CreateSkillsScreen> {
        val viewModel: SkillsViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val viewModelMenu: MenuViewModel = hiltViewModel()

        SkillsScreen(
            uiState = uiState,
            appSideEffectFlow = viewModel.sideEffectChannel,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateUp,
            onNavigateToHomeGraph = {
                viewModelMenu.logout()
                onNavigateToHomeGraph()
            }
        )
    }
}

fun NavController.navigateToCreateSkillsScreen(){
    navigate(MenuScreens.CreateSkillsScreen){
        launchSingleTop = true
    }
}
