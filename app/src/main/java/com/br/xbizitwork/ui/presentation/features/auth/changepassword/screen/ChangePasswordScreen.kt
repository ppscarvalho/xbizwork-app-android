package com.br.xbizitwork.ui.presentation.features.auth.changepassword.screen

import android.app.Activity
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.components.ChangePasswordContent
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.events.ChangePasswordEvent
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.state.ChangePasswordState
import com.br.xbizitwork.ui.presentation.navigation.StateNavigationEffect
import com.br.xbizitwork.ui.theme.BeigeBackground
import kotlinx.coroutines.flow.Flow

/**
 * Tela de alteração de senha
 * Seguindo o mesmo padrão do SignUpScreen
 * Responsável apenas por navegação e binding do ViewModel
 */
@Composable
fun ChangePasswordScreen(
    uiState: ChangePasswordState,
    sideEffectFlow: Flow<AppSideEffect>,
    onEvent: (ChangePasswordEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onCurrentPasswordChanged: (String) -> Unit,
    onNewPasswordChanged: (String) -> Unit,
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

    LifecycleEventEffect(sideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            is AppSideEffect.NavigateToLogin -> {
                // ChangePasswordScreen não trata NavigateToLogin, ignora
            }
            is AppSideEffect.NavigateBack -> {
                // ChangePasswordScreen não trata NavigateBack, ignora
            }
        }
    }

    StateNavigationEffect(
        shouldNavigate = uiState.isSuccess,
        onNavigate = { onNavigateBack() }
    )

    Scaffold(
        content = { paddingValues ->
            ChangePasswordContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onCurrentPasswordChanged = onCurrentPasswordChanged,
                onNewPasswordChanged = onNewPasswordChanged,
                onConfirmPasswordChanged = onConfirmPasswordChanged,
                onChangePasswordClick = { onEvent(ChangePasswordEvent.OnChangePasswordClick) }
            )
        }
    )
}
