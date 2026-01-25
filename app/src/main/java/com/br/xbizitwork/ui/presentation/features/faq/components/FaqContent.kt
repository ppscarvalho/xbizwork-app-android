package com.br.xbizitwork.ui.presentation.features.faq.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.presentation.features.faq.events.FaqEvent
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqUiState
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Conteúdo principal da tela de FAQ
 * Segue o padrão do SkillsContent
 */
@Composable
fun FaqContent(
    modifier: Modifier = Modifier,
    uiState: FaqUiState,
    paddingValues: PaddingValues,
    onEvent: (FaqEvent) -> Unit
) {
    AppGradientBackground(
        modifier = modifier,
        paddingValues = paddingValues
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Dúvidas Frequentes",
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )
            
            FaqContainer(
                modifier = Modifier.fillMaxWidth(),
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }
}
