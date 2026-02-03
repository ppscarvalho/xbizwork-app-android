package com.br.xbizitwork.ui.presentation.features.faq.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.faq.screen.FaqScreen
import com.br.xbizitwork.ui.presentation.features.faq.viewmodel.FaqViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.HomeScreens
import kotlinx.serialization.Serializable

/**
 * Extensão para adicionar a tela de FAQ ao NavGraphBuilder
 */
fun NavGraphBuilder.faqScreen(
    onNavigateBack: () -> Unit
) {
    composable<HomeScreens.FaqScreen> {
        val viewModel: FaqViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        FaqScreen(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateBack
        )
    }
}

/**
 * Extensão para navegar para a tela de FAQ
 */
fun NavController.navigateToFaqScreen() {
    navigate(HomeScreens.FaqScreen) {
        launchSingleTop = true
    }
}
