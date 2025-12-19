package com.br.xbizitwork.ui.presentation.features.auth.signup.screen

import android.app.Activity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.br.xbizitwork.core.sideeffects.SideEffect as AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.auth.signup.components.SignUpContent
import com.br.xbizitwork.ui.presentation.features.auth.signup.events.SignUpEvent
import com.br.xbizitwork.ui.presentation.features.auth.signup.state.SignUpState
import com.br.xbizitwork.ui.presentation.navigation.StateNavigationEffect
import com.br.xbizitwork.ui.theme.BeigeBackground
import kotlinx.coroutines.flow.Flow


@Composable
fun SignUpScreen(
    uiState: SignUpState,
    sideEffectFlow: Flow<AppSideEffect>,
    onEvent: (SignUpEvent) -> Unit,
    onNavigateToSignInScreen: () -> Unit,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current

    // Configura a status bar com fundo bege e ícones escuros
    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window
            window.statusBarColor = BeigeBackground.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    LifecycleEventEffect(sideEffectFlow){ sideEffect ->
        when(sideEffect){
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            is AppSideEffect.NavigateToLogin -> {
                // SignUpScreen não trata NavigateToLogin, ignora
            }
            is AppSideEffect.NavigateBack -> {
                // SignUpScreen não trata NavigateBack, ignora
            }
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