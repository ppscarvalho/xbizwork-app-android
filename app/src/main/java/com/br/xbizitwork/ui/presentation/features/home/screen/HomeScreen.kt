package com.br.xbizitwork.ui.presentation.features.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.bottombar.AppBottomBar
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.home.components.HomeContent
import com.br.xbizitwork.ui.presentation.features.home.state.HomeUIState
import kotlinx.coroutines.flow.Flow

@Composable
fun DefaultScreen(
    uiState: HomeUIState,
    sideEffectFlow: Flow<SideEffect>,  // ✅ NOVO: Flow de side effects
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToUsersConnectionScreen: () -> Unit,
    onNavigateToMenuScreen: () -> Unit,
    onNavigateProfileClick: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current

    // ✅ NOVO: Usar LifecycleEventEffect para tratar SideEffects
    LifecycleEventEffect(sideEffectFlow) { sideEffect ->
        when(sideEffect) {
            is SideEffect.ShowToast -> context.toast(sideEffect.message)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                username = uiState.userName ?: "Usuário",  // ✅ CORRIGIDO: Mostrar valor padrão quando null
                onRightIconClick = {onLogout()}
            )
        },
        bottomBar = {
            AppBottomBar(
                onNavigationToProfileScreen = onNavigateToProfileScreen,
                onNavigationToSearchScreen = onNavigateToSearchScreen,
                onNavigationToUsersConnectionScreen = onNavigateToUsersConnectionScreen,
                onNavigationToMenuScreen = onNavigateToMenuScreen
            )
        },
        content = {paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HomeContent(
                        modifier = Modifier.fillMaxWidth(),
                        onNavigationToSignInScreen = onNavigateToSignInScreen,
                        onNavigateToProfileScreen = { onNavigateProfileClick()}
                    )
                }
            }
        }
    )
}