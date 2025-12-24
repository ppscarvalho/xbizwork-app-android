package com.br.xbizitwork.ui.presentation.features.auth.changepassword.screen

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.inputs.AppPasswordField
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.events.ChangePasswordEvent
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.state.ChangePasswordState
import com.br.xbizitwork.ui.presentation.navigation.StateNavigationEffect
import com.br.xbizitwork.ui.theme.BeigeBackground
import kotlinx.coroutines.flow.Flow
import com.br.xbizitwork.core.sideeffects.AppSideEffect

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
            is AppSideEffect.NavigateBack -> onNavigateBack()
            is AppSideEffect.NavigateToLogin -> {
                // ChangePasswordScreen não trata NavigateToLogin, ignora
            }
        }
    }

    StateNavigationEffect(
        shouldNavigate = uiState.isSuccess,
        onNavigate = { onNavigateBack() }
    )

    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Text(text = "Alterar Senha")

                Spacer(modifier = Modifier.height(16.dp))

                AppPasswordField(
                    value = uiState.currentPassword,
                    onValueChange = onCurrentPasswordChanged,
                    label = "Senha Atual",
                    placeholder = "Digite sua senha atual"
                )

                AppPasswordField(
                    value = uiState.newPassword,
                    onValueChange = onNewPasswordChanged,
                    label = "Nova Senha",
                    placeholder = "Digite sua nova senha"
                )

                AppPasswordField(
                    value = uiState.confirmPassword,
                    onValueChange = onConfirmPasswordChanged,
                    label = "Confirmar Nova Senha",
                    placeholder = "Confirme sua nova senha"
                )

                if (uiState.fieldErrorMessage != null) {
                    Text(
                        text = uiState.fieldErrorMessage,
                        color = androidx.compose.ui.graphics.Color.Red
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                AppButton(
                    text = "Alterar Senha",
                    onClick = { onEvent(ChangePasswordEvent.OnChangePasswordClick) },
                    enabled = uiState.isFormValid && !uiState.isLoading,
                    isLoading = uiState.isLoading
                )

                AppButton(
                    text = "Cancelar",
                    onClick = onNavigateBack,
                    enabled = !uiState.isLoading
                )
            }
        }
    )
}
