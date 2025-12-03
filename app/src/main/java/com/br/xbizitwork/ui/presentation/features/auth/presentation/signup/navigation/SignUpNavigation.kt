package com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.screen.SignUpScreen
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.viewmodel.SignUpViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.AuthScreens
import androidx.compose.runtime.getValue

fun NavGraphBuilder.signUpScreen(
    onNavigateToSignInScreen: () -> Unit,
) {
    composable<AuthScreens.SignUpScreen> {

        val viewModel: SignUpViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sideEffect = viewModel.sideEffectChannel

        SignUpScreen(
            uiState = uiState,
            sideEffectFlow = sideEffect,
            onEvent = viewModel::onEvent,
            onNavigateToSignInScreen = onNavigateToSignInScreen,
            onNameChanged = viewModel::onNameChange,
            onEmailChanged = viewModel::onEmailChange,
            onPasswordChanged = viewModel::onPasswordChange,
            onConfirmPasswordChanged = viewModel::onConfirmPasswordChange
        )
    }
}

fun NavController.navigateToSignUpScreen(){
    navigate(AuthScreens.SignUpScreen)
}
