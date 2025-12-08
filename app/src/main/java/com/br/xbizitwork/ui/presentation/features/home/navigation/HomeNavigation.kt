package com.br.xbizitwork.ui.presentation.features.home.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.home.screen.DefaultScreen
import com.br.xbizitwork.ui.presentation.features.home.viewmodel.HomeViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.HomeScreens

fun NavGraphBuilder.homeScreen(
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToUsersConnectionScreen: () -> Unit,
    onNavigateToMenuScreen: () -> Unit,
    onNavigateProfileClick: () -> Unit,
){
    composable<HomeScreens.HomeScreen> {

        val viewModel: HomeViewModel = hiltViewModel()
        val uiState by viewModel.uState.collectAsStateWithLifecycle()
        val sideEffect = viewModel.sideEffectChannel  // ✅ NOVO: Pegar sideEffectChannel

        DefaultScreen(
            uiState = uiState,
            sideEffectFlow = sideEffect,  // ✅ NOVO: Passar para a Screen
            onNavigateToSignInScreen = onNavigateToSignInScreen,
            onNavigateToProfileScreen = onNavigateToProfileScreen,
            onNavigateToSearchScreen = onNavigateToSearchScreen,
            onNavigateToUsersConnectionScreen = onNavigateToUsersConnectionScreen,
            onNavigateToMenuScreen = onNavigateToMenuScreen,
            onNavigateProfileClick =  onNavigateProfileClick,
            onLogout = {viewModel.logout()}
        )
    }
}
