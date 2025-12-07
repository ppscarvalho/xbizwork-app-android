package com.br.xbizitwork.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.br.xbizitwork.ui.presentation.navigation.RootHost
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.runtime.getValue
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().setKeepOnScreenCondition {
            viewModel.isSlashLoading.value
        }

        setContent {
            XBizWorkTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val navController: NavHostController = rememberNavController()
                RootHost(
                    startDestination = Graphs.HomeGraphs, //uiState.startDestination,
                    navController = navController,
                )
            }
        }
    }
}