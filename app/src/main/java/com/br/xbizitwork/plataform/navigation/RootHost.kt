package com.br.xbizitwork.plataform.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.br.xbizitwork.plataform.navigation.graphs.authGraph
import com.br.xbizitwork.plataform.navigation.graphs.homeGraph
import com.br.xbizitwork.plataform.navigation.screens.Graphs

@Composable
fun RootHost(
    startDestination: Graphs,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        authGraph(
            onNavigateToHomeGraph = {
                navController.navigate(Graphs.HomeGraph)
            }
        )
        homeGraph(
            onNavigateUp = {
                navController.navigateUp()
            }
        )
    }
}