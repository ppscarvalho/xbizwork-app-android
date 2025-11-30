package com.br.xbizitwork.plataform.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.br.xbizitwork.plataform.navigation.screens.Graphs
import com.br.xbizitwork.plataform.navigation.screens.HomeScreens

fun NavGraphBuilder.homeGraph(
    onNavigateUp: () -> Unit
    ){
    navigation<Graphs.HomeGraph>(
        startDestination = HomeScreens.HomeScreen
    ){

    }
}