package com.br.xbizitwork.ui.presentation.navigation.graphs

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import com.br.xbizitwork.ui.presentation.features.menu.navigation.menuScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

/**
 * MenuGraph é um nested navigation graph dentro de HomeGraphs
 * 
 * Contém:
 * - MenuScreen (start destination)
 * - FinancialScreen
 * - CreateScheduleScreen
 * - ViewSchedulesScreen
 * - E outros submenus
 */
fun NavGraphBuilder.menuGraph(
    onNavigateUp: () -> Unit
){
    navigation<Graphs.MenuGraphs>(startDestination = MenuScreens.MenuScreen) {
        menuScreen(
            onNavigateToHomeGraph = onNavigateUp
        )
        
        // Aqui você adicionará as outras screens
        // financialScreen(onNavigateUp = onNavigateUp)
        // createScheduleScreen(onNavigateUp = onNavigateUp)
        // viewSchedulesScreen(onNavigateUp = onNavigateUp)
    }
}

fun NavController.navigateToMenuGraph(
    navOptions: NavOptions? = null
){
    navigate(Graphs.MenuGraphs, navOptions)
}
