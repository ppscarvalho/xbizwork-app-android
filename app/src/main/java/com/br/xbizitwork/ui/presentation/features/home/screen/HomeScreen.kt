package com.br.xbizitwork.ui.presentation.features.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.bottombar.AppBottomBar
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.home.components.HomeContent

@Composable
fun DefaultScreen(
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToUsersConnectionScreen: () -> Unit,
    onNavigateToMenuScreen: () -> Unit,
    onNavigateProfileClick: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                username = "Pedro Carvalho",
                onRightIconClick = onNavigateToProfileScreen
            )
        },
        bottomBar = {
            AppBottomBar(
                onNavigationToProfileScreen = onNavigateToProfileScreen,
                onNavigationToSearchScreen = onNavigateToSearchScreen,
                onNavigationToUsersConnectionScreen = onNavigateToUsersConnectionScreen,
                onNavigationToMenuScreen = onNavigateToMenuScreen
            )
        },
        content = {paddingValues ->
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HomeContent(
                        modifier = Modifier.fillMaxWidth(),
                        onNavigationToSignInScreen = onNavigateToSignInScreen,
                        onNavigateToProfileScreen = { onNavigateProfileClick()}
                    )
                }
            }
        }
    )
}