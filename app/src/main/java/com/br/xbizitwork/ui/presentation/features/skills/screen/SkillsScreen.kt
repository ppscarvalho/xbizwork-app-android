package com.br.xbizitwork.ui.presentation.features.skills.screen

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.skills.components.SkillsContent
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun SkillsScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Minhas habilidades",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        content = { paddingValues ->
            SkillsContent(
                paddingValues = paddingValues,
                onSaveClick = { selectedSkills ->
                    // por enquanto só log / toast
                }
            )
        }
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4
)
@Composable
private fun SkillsScreenPreview() {
    XBizWorkTheme {
        SkillsScreen(
            onNavigateBack = {}
        )
    }
}
