package com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.screen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.components.SignUpContent
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.state.SignUpState
import com.br.xbizitwork.ui.presentation.navigation.NavDestinationHelp

@Composable
fun SignUpScreen(
    uiState: SignUpState,
    onNavigateToSignInScreen: () -> Unit,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    NavDestinationHelp(
        shouldNavigate = { uiState.isSuccess },
        destination = { onNavigateToSignInScreen() }
    )

    Scaffold(
        content = {paddingValues ->
            SignUpContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onNameChanged = onNameChanged,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                onConfirmPasswordChanged = onConfirmPasswordChanged,
                onSignUpClick = onSignUpClick,
                onNavigateToSignInScreen = onNavigateToSignInScreen,
            )
        }
    )
}