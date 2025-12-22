package com.br.xbizitwork.ui.presentation.features.profile.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.compose.runtime.getValue
import com.br.xbizitwork.ui.presentation.features.profile.screen.EditProfileScreen
import com.br.xbizitwork.ui.presentation.features.profile.viewmodel.EditProfileViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.HomeScreens

fun NavGraphBuilder.editProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable<HomeScreens.EditProfileScreen> {

        val viewModel: EditProfileViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sideEffect = viewModel.sideEffectChannel

        EditProfileScreen(
            uiState = uiState,
            appSideEffectFlow = sideEffect,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateBack,
            onNavigateToLogin = onNavigateToLogin
        )
    }
}

fun NavController.navigateToEditProfileScreen() {
    navigate(HomeScreens.EditProfileScreen) {
        launchSingleTop = true
    }
}
