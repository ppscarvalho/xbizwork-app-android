package com.br.xbizitwork.ui.presentation.features.auth.signup.screen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.features.auth.signup.components.SignUpContent
import com.br.xbizitwork.ui.presentation.features.auth.signup.events.SignUpEvent
import com.br.xbizitwork.ui.presentation.features.auth.signup.state.SignUpState
import com.br.xbizitwork.ui.presentation.navigation.StateNavigationEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun SignUpScreen(
    uiState: SignUpState,
    sideEffectFlow: Flow<SideEffect>,
    onEvent: (SignUpEvent) -> Unit,
    onNavigateToSignInScreen: () -> Unit,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit
) {

    val context = LocalContext.current
    LifecycleEventEffect(sideEffectFlow){ sideEffect ->
        when(sideEffect){
            is SideEffect.ShowToast -> context.toast(sideEffect.message)
        }
    }

    StateNavigationEffect(
        shouldNavigate = uiState.isSuccess,
        onNavigate = { onNavigateToSignInScreen() }
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
                onSignUpClick = {onEvent(SignUpEvent.OnSignUpClick)},
                onNavigateToSignInScreen = onNavigateToSignInScreen,
            )
        }
    )
}