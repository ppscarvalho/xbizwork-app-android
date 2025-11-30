package com.br.xbizitwork.plataform

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.br.xbizitwork.plataform.navigation.RootHost
import com.br.xbizitwork.plataform.navigation.screens.Graphs
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            XBizWorkTheme {
                val navController: NavHostController = rememberNavController()
                RootHost(
                    startDestination = Graphs.AuthGraph,
                    navController = navController,
                )
            }
        }
    }
}