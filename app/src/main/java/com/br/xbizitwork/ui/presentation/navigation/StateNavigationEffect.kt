package com.br.xbizitwork.ui.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun StateNavigationEffect(
    shouldNavigate: Boolean,
    onNavigate: () -> Unit
) {
    LaunchedEffect(shouldNavigate) {
        if (shouldNavigate) {
            onNavigate()
        }
    }
}
