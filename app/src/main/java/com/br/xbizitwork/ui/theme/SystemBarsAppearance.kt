package com.br.xbizitwork.ui.theme

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Configura a aparência das barras do sistema (status bar e navigation bar)
 * 
 * @param statusBarColor Cor da barra de status. Se null, não altera.
 * @param navigationBarColor Cor da barra de navegação. Se null, não altera.
 * @param darkStatusBarIcons Se true, ícones da status bar ficam escuros (para fundos claros)
 * @param darkNavigationBarIcons Se true, ícones da navigation bar ficam escuros (para fundos claros)
 */
@Composable
fun SetSystemBarsAppearance(
    statusBarColor: Color? = null,
    navigationBarColor: Color? = null,
    darkStatusBarIcons: Boolean = true,
    darkNavigationBarIcons: Boolean = true,
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val insetsController = WindowCompat.getInsetsController(window, view)

            // Configurar cores das barras
            statusBarColor?.let {
                window.statusBarColor = it.toArgb()
            }
            navigationBarColor?.let {
                window.navigationBarColor = it.toArgb()
            }

            // Configurar aparência dos ícones
            insetsController?.isAppearanceLightStatusBars = darkStatusBarIcons
            insetsController?.isAppearanceLightNavigationBars = darkNavigationBarIcons
        }
    }
}

/**
 * Alias para compatibilidade com nomes alternativos
 */
@Composable
fun SetSystemBarsAppearanceWithIcons(
    darkStatusBarIcons: Boolean,
    darkNavigationBarIcons: Boolean,
) {
    SetSystemBarsAppearance(
        statusBarColor = null,
        navigationBarColor = null,
        darkStatusBarIcons = darkStatusBarIcons,
        darkNavigationBarIcons = darkNavigationBarIcons
    )
}
