package com.br.xbizitwork.ui.presentation.features.home.screen

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.br.xbizitwork.core.sideeffects.AppSideEffect as AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.bottombar.AppBottomBar
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.home.components.HomeContent
import com.br.xbizitwork.ui.presentation.features.home.state.HomeUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import kotlinx.coroutines.flow.Flow

// Cor da AppTopBar
private val TopBarColor = Color(0xFF082f49)

@Composable
fun DefaultScreen(
    uiState: HomeUIState,
    sideEffectFlow: Flow<AppSideEffect>,
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigationToFaqScreen: () -> Unit,
    onNavigateToMenuScreen: () -> Unit,
    onNavigateProfileClick: () -> Unit,
    onNavigateToPlansScreen: () -> Unit,
    onNavigationToSearchProfessionalSkillScreen: () -> Unit
) {
    val context = LocalContext.current
    val view = LocalView.current

    // Configura a status bar com fundo escuro e ícones claros
    if (!view.isInEditMode) {
        SideEffect {
            val window = (context as Activity).window
            window.statusBarColor = TopBarColor.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    // Usar LifecycleEventEffect para tratar SideEffects
    LifecycleEventEffect(sideEffectFlow) { sideEffect ->
        when(sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            is AppSideEffect.NavigateToHomeGraph -> {
                // HomeScreen não trata NavigateToLogin, ignora
            }
            is AppSideEffect.NavigateBack -> {
                // HomeScreen não trata NavigateBack, ignora
            }
            is AppSideEffect.OpenExternalUrl -> {
                // HomeScreen não abre URLs externas, ignora
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                username = if (uiState.userName.isNullOrEmpty()) "Usuário" else uiState.userName,
                onRightIconClick = {
                    if (uiState.userName.isNullOrEmpty()) {
                        onNavigateToSignInScreen()  // Deslogado → Login
                    } else {
                        onNavigateToProfileScreen()  // Logado → Perfil
                    }
                }
            )
        },
        bottomBar = {
            AppBottomBar(
                isLoggedIn = !uiState.userName.isNullOrEmpty(),
                onNavigationToProfileScreen = onNavigateToProfileScreen,
                onNavigationToSearchScreen = onNavigateToSearchScreen,
                onNavigationToFaqScreen = onNavigationToFaqScreen,
                onNavigationToMenuScreen = onNavigateToMenuScreen,
                onNavigationToSearchProfessionalSkillScreen = onNavigationToSearchProfessionalSkillScreen
            )
        },
        content = {paddingValues ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HomeContent(
                        modifier = Modifier.fillMaxWidth(),
                        paddingValues = paddingValues,
                        onNavigateToPlansScreen = onNavigateToPlansScreen,
                        onNavigateToProfileScreen = { onNavigateProfileClick()}
                    )
                }
        }
    )
}

@Preview
@Composable
private fun DefaultScreenPreview() {
    XBizWorkTheme{
        DefaultScreen(
            uiState = HomeUIState(
                userName = "João Silva"
            ),
            sideEffectFlow = kotlinx.coroutines.flow.flowOf(),
            onNavigateToSignInScreen = {},
            onNavigateToProfileScreen = {},
            onNavigateToSearchScreen = {},
            onNavigationToFaqScreen = {},
            onNavigateToMenuScreen = {},
            onNavigateProfileClick = {},
            onNavigateToPlansScreen = {},
            onNavigationToSearchProfessionalSkillScreen = {}
        )
    }
    
}