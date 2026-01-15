package com.br.xbizitwork.ui.presentation.features.profile.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.profile.components.EditProfileContent
import com.br.xbizitwork.ui.presentation.features.profile.events.EditProfileEvent
import com.br.xbizitwork.ui.presentation.features.profile.state.EditProfileUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


/**
 * Tela de Edição de Perfil do Usuário
 * 
 * Responsabilidades:
 * - Exibir formulário de edição de dados cadastrais
 * - Gerenciar status bar color e appearance
 * - Conectar eventos da UI ao ViewModel
 * - Processar side effects (Toast, navegação)
 * - Permitir voltar à tela anterior
 * - Navegar ao login se token expirar (401)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    uiState: EditProfileUIState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (EditProfileEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToHomeGraph: () -> Unit,
) {
    val context = LocalContext.current

    // Side effects (igual SignUp)
    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)

            is AppSideEffect.NavigateToHomeGraph -> {
                onNavigateToHomeGraph()
            }

            is AppSideEffect.NavigateBack -> {
                onNavigateBack()
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Editar Perfil",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = {
                    onEvent(EditProfileEvent.OnCancelClick)
                }
            )
        },
        content = { paddingValues ->
            EditProfileContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onNameChanged = { onEvent(EditProfileEvent.OnNameChanged(it)) },
                onCpfChanged = { onEvent(EditProfileEvent.OnCpfChanged(it)) },
                onDateOfBirthChanged = { onEvent(EditProfileEvent.OnDateOfBirthChanged(it)) },
                onEmailChanged = { onEvent(EditProfileEvent.OnEmailChanged(it)) },
                onPhoneChanged = { onEvent(EditProfileEvent.OnPhoneChanged(it)) },
                onZipCodeChanged = { onEvent(EditProfileEvent.OnZipCodeChanged(it)) },
                onZipCodeBlur = { onEvent(EditProfileEvent.OnZipCodeBlur) },
                onAddressChanged = { onEvent(EditProfileEvent.OnAddressChanged(it)) },
                onNumberChanged = { onEvent(EditProfileEvent.OnNumberChanged(it)) },
                onNeighborhoodChanged = { onEvent(EditProfileEvent.OnNeighborhoodChanged(it)) },
                onCityChanged = { onEvent(EditProfileEvent.OnCityChanged(it)) },
                onStateChanged = { onEvent(EditProfileEvent.OnStateChanged(it)) },
                onSaveClick = { onEvent(EditProfileEvent.OnSaveClick) },
                onCancelClick = { onEvent(EditProfileEvent.OnCancelClick) }
            )
        }
    )
}

@Preview
@Composable
private fun EditProfileScreenPreview() {
    XBizWorkTheme {
        EditProfileScreen(
            uiState = EditProfileUIState(),
            appSideEffectFlow = flowOf() ,
            onEvent = {},
            onNavigateBack = {},
            onNavigateToHomeGraph = {}
        )
    }
}