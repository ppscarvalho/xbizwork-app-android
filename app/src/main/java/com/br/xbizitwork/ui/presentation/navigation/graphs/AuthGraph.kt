package com.br.xbizitwork.ui.presentation.navigation.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.br.xbizitwork.ui.presentation.features.auth.signin.navigation.signInScreen
import com.br.xbizitwork.ui.presentation.features.auth.signup.navigation.signUpScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.AuthScreens
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs

fun NavGraphBuilder.authGraph(
    onNavigateToHomeGraph: (NavOptions) -> Unit,
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
){
    navigation<Graphs.AuthGraphs>(
        startDestination = AuthScreens.SignInScreen
    ){
        signInScreen(
            onNavigateToHomeGraph = { onNavigateToHomeGraph(navOptions{
                popUpTo(Graphs.AuthGraphs)
            }) },
            onNavigateToSignUpScreen = onNavigateToSignUpScreen
        )
        signUpScreen(
            onNavigateToSignInScreen = onNavigateToSignInScreen
        )
    }
}
