package com.br.xbizitwork.ui.presentation.features.menu.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.menu.screen.MenuScreen
import com.br.xbizitwork.ui.presentation.features.menu.viewmodel.MenuViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.menuScreen(
    onNavigateToHomeGraph: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    onNavigateToListSchedulesScreen: () -> Unit,
    onNavigateToCreateSkills: () -> Unit,
    onNavigateToProfessionalAgendaScreen: () -> Unit,
    onNavigateToChangePasswordScreen: () -> Unit
){
    composable<MenuScreens.MenuScreen> {
        val viewModel: MenuViewModel = hiltViewModel()
        val sideEffect = viewModel.sideEffectChannel

        MenuScreen(
            onNavigateToHomeGraph = onNavigateToHomeGraph,
            appSideEffectFlow = sideEffect,
            onClickUpdateProfile = { onNavigateToEditProfile() },
            onClickChangePassword = {onNavigateToChangePasswordScreen()},
            onClickCreateSkills = {onNavigateToCreateSkills()},
            onClickSetupSchedule = { onNavigateToListSchedulesScreen() },
            onClickYourPlan = {},
            onClickMyAppointments = {},
            onClickProfessionalAgenda = { onNavigateToProfessionalAgendaScreen() },
            onClickAppVersion = {},
            onClickRateApp = {},
            onClickLogout = { 
                viewModel.logout()
                onNavigateToHomeGraph()
            }
        )
    }
}

fun NavController.navigateToMenuScreen(){
    navigate(MenuScreens.MenuScreen){
        launchSingleTop = true
    }
}
