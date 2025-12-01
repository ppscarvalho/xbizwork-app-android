package com.br.xbizitwork.ui.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signin.navigation.signInScreen
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.navigation.signUpScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.AuthScreens
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs

fun NavGraphBuilder.authGraph(
    onNavigateToHomeGraph: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
){
    navigation<Graphs.AuthGraph>(
        startDestination = AuthScreens.SignInScreen
    ){
        signInScreen(
            onNavigateToHomeGraph = onNavigateToHomeGraph,
            onNavigateToSignUpScreen = onNavigateToSignUpScreen
        )
        signUpScreen(
            onNavigateToSignInScreen = {}
        )
    }
}
