package com.br.xbizitwork.ui.presentation.features.auth.presentation.signin.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.navigation.screens.AuthScreens

fun NavGraphBuilder.signInScreen(
    onNavigateToHomeGraph: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
) {
    composable<AuthScreens.SignInScreen> {
        SignIn {
            onNavigateToSignUpScreen()
        }
    }
}

@Composable
fun SignIn(
    modifier: Modifier = Modifier,
    onNavigateToHomeGraph: () -> Unit,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = { onNavigateToHomeGraph() },
        ) {
            Text(
                text = "Login"
            )
        }
    }
}