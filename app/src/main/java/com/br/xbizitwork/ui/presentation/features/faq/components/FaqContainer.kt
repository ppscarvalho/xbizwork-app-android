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
 * Container principal do FAQ que gerencia estados de loading e erro
 * Segue o padrão do SkillsContainer
 */
@Composable
fun FaqContainer(
    modifier: Modifier = Modifier,
    uiState: FaqUiState,
    onEvent: (FaqEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                LoadingIndicator(
                    message = "Carregando FAQ..."
                )
            }
            
            uiState.errorMessage != null -> {
                // Exibe mensagem de erro
                Text(
                    text = uiState.errorMessage,
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            
            uiState.sections.isEmpty() -> {
                // Nenhuma seção disponível
                Text(
                    text = "Nenhuma dúvida frequente disponível no momento",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            }
            
            else -> {
                // Exibe as seções
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    uiState.sections.forEach { section ->
                        FaqSectionItem(
                            section = section,
                            onToggleSection = {
                                onEvent(FaqEvent.OnToggleSection(section.id))
                            },
                            onToggleQuestion = { questionId ->
                                onEvent(FaqEvent.OnToggleQuestion(section.id, questionId))
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
