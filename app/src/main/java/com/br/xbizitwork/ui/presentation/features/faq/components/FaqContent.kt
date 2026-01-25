package com.br.xbizitwork.ui.presentation.features.faq.components

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.presentation.features.faq.events.FaqEvent
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqQuestionUiState
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqSectionUiState
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Main content component for FAQ screen
 * Following the same pattern as SkillsContent
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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0f344e
)
@Composable
private fun FaqContentPreview() {
    XBizWorkTheme {
        FaqContent(
            uiState = FaqUiState(
                isLoading = false,
                sections = listOf(
                    FaqSectionUiState(
                        id = 1,
                        title = "Sobre Cadastro e Acesso",
                        description = "Perguntas relacionadas ao cadastro",
                        order = 1,
                        questions = listOf(
                            FaqQuestionUiState(
                                id = 1,
                                question = "Como me cadastro no app?",
                                answer = "Baixe o app na loja de aplicativos...",
                                order = 1
                            ),
                            FaqQuestionUiState(
                                id = 2,
                                question = "Posso me cadastrar como cliente e fornecedor?",
                                answer = "Sim, mas você precisará usar contas separadas...",
                                order = 2
                            )
                        )
                    )
                )
            ),
            paddingValues = PaddingValues(10.dp),
            onEvent = {}
        )
    }
}
