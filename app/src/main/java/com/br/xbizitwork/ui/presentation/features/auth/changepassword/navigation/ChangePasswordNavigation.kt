package com.br.xbizitwork.ui.presentation.features.auth.changepassword.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.screen.ChangePasswordScreen
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.viewmodel.ChangePasswordViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.HomeScreens

fun NavGraphBuilder.changePasswordScreen(
    onNavigateBack: () -> Unit = {}
) {
    composable<HomeScreens.ChangePasswordScreen> {
        val viewModel: ChangePasswordViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sideEffect = viewModel.sideEffectChannel

        ChangePasswordScreen(
            uiState = uiState,
            appSideEffectFlow = sideEffect,
            onEvent = viewModel::onEvent,
            onCurrentPasswordChanged = viewModel::onCurrentPasswordChange,
            onNewPasswordChanged = viewModel::onNewPasswordChange,
            onConfirmPasswordChanged = viewModel::onConfirmPasswordChange,
            onNavigateBack = onNavigateBack
        )
    }
}

fun NavController.navigateToChangePasswordScreen() {
    navigate(HomeScreens.ChangePasswordScreen) {
        launchSingleTop = true
    }
}
