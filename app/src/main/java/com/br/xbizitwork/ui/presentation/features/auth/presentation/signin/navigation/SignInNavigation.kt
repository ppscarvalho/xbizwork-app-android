package com.br.xbizitwork.ui.presentation.features.auth.presentation.signin.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signin.screen.SignInScreen
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signin.viewmodel.SignInViewModel
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.screen.SignUpScreen
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.viewmodel.SignUpViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.AuthScreens

fun NavGraphBuilder.signInScreen(
    onNavigateToHomeGraph: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
) {
    composable<AuthScreens.SignInScreen> {
        val viewModel: SignInViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sideEffect = viewModel.sideEffectChannel

        SignInScreen(
            uiState = uiState,
            sideEffectFlow = sideEffect,
            onEvent = viewModel::onEvent,
            onNavigateToSignUpScreen = onNavigateToSignUpScreen,
            onEmailChanged = viewModel::onEmailChange,
            onPasswordChanged = viewModel::onPasswordChange,
            onNavigateToHomeGraph = onNavigateToHomeGraph
        )
    }
}

fun NavController.navigateToSignInScreen(){
    navigate(AuthScreens.SignInScreen){
        popUpTo(0)
    }
}
