package com.br.xbizitwork.ui.presentation.features.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.bottombar.AppBottomBar
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.home.components.HomeContent

@Composable
fun DefaultScreen(
    onLogout:() -> Unit,
    onNavigationToProfileScreen: () -> Unit,
    onNavigationToSearchScreen: () -> Unit,
    onNavigationToUsersConnectionScreen: () -> Unit,
    onNavigationToMenuScreen: () -> Unit,
    onProfileClick: () -> Unit,
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                username = "Pedro Carvalho",
                onRightIconClick = {onLogout()}
            )
        },
        bottomBar = {
            AppBottomBar(
                onNavigationToProfileScreen = onNavigationToProfileScreen,
                onNavigationToSearchScreen = onNavigationToSearchScreen,
                onNavigationToUsersConnectionScreen = onNavigationToUsersConnectionScreen,
                onNavigationToMenuScreen = onNavigationToMenuScreen
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
                        onNavigateToSignScreen = {onNavigationToProfileScreen()},
                        onProfileClick = { onProfileClick()}
                    )
                }
            }
        }
    )
}