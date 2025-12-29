package com.br.xbizitwork.ui.presentation.features.schedule.search.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.schedule.search.screen.SearchScheduleScreen
import com.br.xbizitwork.ui.presentation.features.schedule.search.viewmodel.SearchScheduleViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.HomeScreens

fun NavGraphBuilder.searchScheduleScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToScheduleDetail: (String) -> Unit
){
    composable<HomeScreens.SearchScreen>{
        val viewModel: SearchScheduleViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        SearchScheduleScreen(
            uiState = uiState,
            onNavigateBack = onNavigateBack,
            onNavigateToScheduleDetail = onNavigateToScheduleDetail,
            onEvent = viewModel::onEvent
        )
    }
}

fun NavController.navigateToSearchScheduleScreen(){
    navigate(HomeScreens.SearchScreen)
}