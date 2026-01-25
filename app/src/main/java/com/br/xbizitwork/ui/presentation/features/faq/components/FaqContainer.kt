package com.br.xbizitwork.ui.presentation.features.faq.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.faq.events.FaqEvent
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqUiState
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Container principal para a lista de seções de FAQ
 * Gerencia estados de loading e erro
 */
@Composable
fun FaqContainer(
    modifier: Modifier = Modifier,
    uiState: FaqUiState,
    onEvent: (FaqEvent) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                LoadingIndicator(
                    message = "Carregando FAQ..."
                )
            }
            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Erro ao carregar FAQ",
                        fontFamily = poppinsFontFamily,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = uiState.errorMessage,
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
            uiState.sections.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Nenhuma seção de FAQ disponível",
                        fontFamily = poppinsFontFamily,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center
                    )
                }
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Ordena seções pelo campo order antes de renderizar
                    uiState.sections.sortedBy { it.order }.forEach { section ->
                        FaqSectionItem(
                            section = section,
                            onToggleSection = {
                                onEvent(FaqEvent.OnToggleSection(section.id))
                            },
                            onToggleQuestion = { questionId ->
                                onEvent(FaqEvent.OnToggleQuestion(section.id, questionId))
                            }
                        )
                    }
                }
            }
        }
    }
}
