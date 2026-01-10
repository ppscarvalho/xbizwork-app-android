package com.br.xbizitwork.ui.presentation.features.auth.signin.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.auth.signin.components.SignInContent
import com.br.xbizitwork.ui.presentation.features.auth.signin.events.SignInEvent
import com.br.xbizitwork.ui.presentation.features.auth.signin.state.SignInState
import com.br.xbizitwork.ui.presentation.navigation.StateNavigationEffect
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SignInScreen(
    uiState: SignInState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (SignInEvent) -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onNavigateToHomeGraph: () -> Unit,
    onNavigateBack: () -> Unit = {}
) {

    val context = LocalContext.current
    LifecycleEventEffect(appSideEffectFlow){ sideEffect ->
        when(sideEffect){
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            is AppSideEffect.NavigateToLogin -> {
                // Já estamos na tela de login, não faz nada
            }
            is AppSideEffect.NavigateBack -> {
                // Não usado nesta tela, mas precisa estar aqui por causa do sealed interface
            }
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
                onNavigationIconButton = { onNavigateBack() }
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

@Preview(
    showBackground = true,
    device = Devices.PIXEL_7
)
@Composable
fun SignInScreenPreview() {
    XBizWorkTheme {
        SignInScreen(
            uiState = SignInState(
                email = "user@email.com",
                password = "123456",
                isFormValid = true,
                isLoading = false,
                isSuccess = false
            ),
            appSideEffectFlow = flowOf(),
            onEvent = {},
            onNavigateToSignUpScreen = {},
            onEmailChanged = {},
            onPasswordChanged = {},
            onNavigateToHomeGraph = {},
            onNavigateBack = {}
        )
    }
}