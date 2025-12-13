package com.br.xbizitwork.ui.presentation.features.menu.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.menu.screen.MenuScreen
import com.br.xbizitwork.ui.presentation.features.menu.viewmodel.MenuViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.menuScreen(
    onNavigateToHomeGraph: () -> Unit
){
    composable<MenuScreens.MenuScreen> {
        val viewModel: MenuViewModel = hiltViewModel()
        val sideEffect = viewModel.sideEffectChannel

        MenuScreen(
            onNavigateToHomeGraph = onNavigateToHomeGraph,
            sideEffectFlow = sideEffect,
            onClickUpdateProfile = {},
            onClickChangerPassword = {},
            onClickDateRange = {},
            onClickAssignment = {},
            onClickEvent = {},
            onClickViewModule = {},
            onClickFAQ = {},
            onClickAppVersion = {},
            onClickRateApp = {},
            onClickLogout = { viewModel.logout() }
        )
    }
}

fun NavController.navigateToMenuScreen(){
    navigate(MenuScreens.MenuScreen){
        launchSingleTop = true
    }
}
