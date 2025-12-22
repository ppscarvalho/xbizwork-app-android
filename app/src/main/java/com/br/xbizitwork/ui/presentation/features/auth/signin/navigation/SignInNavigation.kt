package com.br.xbizitwork.ui.presentation.features.auth.signin.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.auth.signin.screen.SignInScreen
import com.br.xbizitwork.ui.presentation.features.auth.signin.viewmodel.SignInViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.AuthScreens

fun NavGraphBuilder.signInScreen(
    onNavigateToHomeGraph: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
    onNavigateBack: () -> Unit = {}
) {
    composable<AuthScreens.SignInScreen> {
        val viewModel: SignInViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sideEffect = viewModel.sideEffectChannel

        SignInScreen(
            uiState = uiState,
            appSideEffectFlow = sideEffect,
            onEvent = viewModel::onEvent,
            onNavigateToSignUpScreen = onNavigateToSignUpScreen,
            onEmailChanged = viewModel::onEmailChange,
            onPasswordChanged = viewModel::onPasswordChange,
            onNavigateToHomeGraph = onNavigateToHomeGraph,
            onNavigateBack = onNavigateBack
        )
    }
}

fun NavController.navigateToSignInScreen(clearBackStack: Boolean = false){
    navigate(AuthScreens.SignInScreen){
        if (clearBackStack) {
            popUpTo(0) { inclusive = true }
        } else {
            popUpTo(0)
        }
        launchSingleTop = true
    }
}
