package com.br.xbizitwork.plataform.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.br.xbizitwork.plataform.navigation.screens.AuthScreens
import com.br.xbizitwork.plataform.navigation.screens.Graphs

fun NavGraphBuilder.authGraph(
    onNavigateToHomeGraph: () -> Unit
){
    navigation<Graphs.AuthGraph>(
        startDestination = AuthScreens.LoginScreen
    ){

    }
}