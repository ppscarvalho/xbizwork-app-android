package com.br.xbizitwork.ui.presentation.features.auth.changepassword.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.components.ChangePasswordContent
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.events.ChangePasswordEvent
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.state.ChangePasswordState
import com.br.xbizitwork.ui.presentation.navigation.StateNavigationEffect
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

    LifecycleEventEffect(sideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            is AppSideEffect.NavigateToHomeGraph -> {
                // ChangePasswordScreen não trata NavigateToLogin, ignora
            }
            is AppSideEffect.NavigateBack -> {
                // ChangePasswordScreen não trata NavigateBack, ignora
            }
            is AppSideEffect.OpenExternalUrl -> {
                // ChangePasswordScreen não trata OpenExternalUrl, ignora
            }
        }
    }

    StateNavigationEffect(
        shouldNavigate = uiState.isSuccess,
        onNavigate = { onNavigateBack() }
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Alteração de Senha",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = { onNavigateBack() }
            )
        },
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
