package com.br.xbizitwork.ui.presentation.features.auth.signin.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.auth.signin.components.SignInContent
import com.br.xbizitwork.ui.presentation.features.auth.signin.events.SignInEvent
import com.br.xbizitwork.ui.presentation.features.auth.signin.state.SignInState
import com.br.xbizitwork.ui.presentation.navigation.StateNavigationEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun SignInScreen(
    uiState: SignInState,
    sideEffectFlow: Flow<SideEffect>,
    onEvent: (SignInEvent) -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onNavigateToHomeGraph: () -> Unit
) {

    val context = LocalContext.current
    LifecycleEventEffect(sideEffectFlow){ sideEffect ->
        when(sideEffect){
            is SideEffect.ShowToast -> context.toast(sideEffect.message)
        }
    }

    StateNavigationEffect(
        shouldNavigate = uiState.isSuccess,
        onNavigate = { onNavigateToHomeGraph() }
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Login",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = { onNavigateToHomeGraph() }
            )
        },
        content = {paddingValues ->
            SignInContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                onSignInClick = {onEvent(SignInEvent.OnSignInClick)},
                onNavigateToSignUpScreen = onNavigateToSignUpScreen
            )
        }
    )
}