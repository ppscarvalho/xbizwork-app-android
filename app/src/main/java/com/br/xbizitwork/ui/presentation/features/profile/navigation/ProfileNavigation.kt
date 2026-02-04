package com.br.xbizitwork.ui.presentation.features.profile.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.menu.viewmodel.MenuViewModel
import com.br.xbizitwork.ui.presentation.features.profile.screen.EditProfileScreen
import com.br.xbizitwork.ui.presentation.features.profile.viewmodel.EditProfileViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.editProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHomeGraph: () -> Unit,
) {
    composable<MenuScreens.EditProfileScreen> {

        val viewModel: EditProfileViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sideEffect = viewModel.sideEffectChannel
        val viewModelMenu: MenuViewModel = hiltViewModel()

        EditProfileScreen(
            uiState = uiState,
            appSideEffectFlow = sideEffect,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateBack,
            onNavigateToHomeGraph = {
                viewModelMenu.logout()
                onNavigateToHomeGraph()
            }
        )
    }
}

fun NavController.navigateToEditProfileScreen() {
    navigate(MenuScreens.EditProfileScreen) {
        launchSingleTop = true
    }
}
