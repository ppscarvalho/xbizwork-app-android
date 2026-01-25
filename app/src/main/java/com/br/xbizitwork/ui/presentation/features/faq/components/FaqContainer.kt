package com.br.xbizitwork.ui.presentation.features.faq.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.faq.events.FaqEvent
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqUiState

/**
 * Container component for FAQ sections
 * Handles loading state and renders section list
 * Following the same pattern as SkillsContainer
 */
@Composable
fun FaqContainer(
    modifier: Modifier = Modifier,
    uiState: FaqUiState,
    onEvent: (FaqEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            uiState.isLoading -> {
                LoadingIndicator(
                    message = "Carregando FAQ..."
                )
            }
            else -> {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    uiState.sections.forEach { section ->
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
