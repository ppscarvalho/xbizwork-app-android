package com.br.xbizitwork.ui.presentation.features.profile.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.profile.viewmodel.EditProfileViewModel
import com.br.xbizitwork.ui.presentation.features.profile.views.EditProfileScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.HomeScreens

fun NavGraphBuilder.editProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    composable<HomeScreens.EditProfileScreen> {
        val viewModel: EditProfileViewModel = hiltViewModel()

        EditProfileScreen(
            onNavigateBack = onNavigateBack,
            onNavigateToLogin = onNavigateToLogin,
            viewModel = viewModel
        )
    }
}

fun NavController.navigateToEditProfileScreen() {
    navigate(HomeScreens.EditProfileScreen) {
        launchSingleTop = true
    }
}

