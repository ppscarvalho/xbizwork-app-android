package com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.components.AuthBottomSheetContent
import com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.events.AuthBottomSheetEvent
import com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.state.AuthBottomSheetState
import kotlinx.coroutines.flow.Flow

/**
 * Screen/Wrapper do BottomSheet de autenticação
 * Segue o padrão de composables do projeto
 * Não injeta ViewModel - recebe estado e callbacks
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthBottomSheetScreen(
    isVisible: Boolean,
    uiState: AuthBottomSheetState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onEvent: (AuthBottomSheetEvent) -> Unit,
    onDismiss: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    if (!isVisible) return

    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    // Observar SideEffects
    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            else -> {}
        }
    }

    // Observar sucesso do login
    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onLoginSuccess()
            onEvent(AuthBottomSheetEvent.OnDismiss)
        }
    }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
            onEvent(AuthBottomSheetEvent.OnDismiss)
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        AuthBottomSheetContent(
            uiState = uiState,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onEvent = onEvent
        )
    }
}
