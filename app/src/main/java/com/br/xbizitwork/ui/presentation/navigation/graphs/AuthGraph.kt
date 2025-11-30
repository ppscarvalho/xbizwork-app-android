package com.br.xbizitwork.ui.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.br.xbizitwork.ui.presentation.navigation.screens.AuthScreens
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs

fun NavGraphBuilder.authGraph(
    onNavigateToHomeGraph: () -> Unit
){
    navigation<Graphs.AuthGraph>(
        startDestination = AuthScreens.LoginScreen
    ){

    }
}