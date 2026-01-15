package com.br.xbizitwork.ui.presentation.components.background

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.Blue200
import com.br.xbizitwork.ui.theme.Blue700
import com.br.xbizitwork.ui.theme.Blue900

val AppGradientColors = listOf(
    Blue900,
    Blue700,
    Blue200
)

@Composable
fun AppGradientBackground(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    colors: List<Color> = AppGradientColors,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(colors = colors)
            )
            .padding(paddingValues),
        content = content
    )
}

