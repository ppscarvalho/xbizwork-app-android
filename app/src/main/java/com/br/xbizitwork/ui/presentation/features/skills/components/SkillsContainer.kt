package com.br.xbizitwork.ui.presentation.features.skills.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.domain.result.category.CategoryResult
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.skills.events.SkillsEvent
import com.br.xbizitwork.ui.presentation.features.skills.state.SkillItemUiState
import com.br.xbizitwork.ui.presentation.features.skills.state.SkillUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun SkillsContainer(
    modifier: Modifier = Modifier,
    uiState: SkillUiState,
    onEvent: (SkillsEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        when{
            uiState.isLoading -> {
                LoadingIndicator(
                    message = "Carregando categorias..."
                )
            }
            else -> {
                // Merge: cria lista de skills com checked baseado em savedSkillIds
                var skills by remember(uiState.categories, uiState.savedSkillIds) {
                    mutableStateOf(
                        uiState.categories.map { category ->
                            SkillItemUiState(
                                id = category.id,
                                description = category.description,
                                checked = category.id in uiState.savedSkillIds // ✅ Merge aqui
                            )
                        }
                    )
                }

                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    SkillSwitchList(
                        skills = skills,
                        onSkillCheckedChange = { id, checked ->
                            skills = skills.map { skill ->
                                if (skill.id == id) skill.copy(checked = checked) else skill
                            }
                        }
                    )

                    AppButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        text = "Salvar habilidades",
                        enabled = skills.any { it.checked },
                        isLoading = uiState.isLoading,
                        contentColor = Color.White,
                        onClick = {
                            // Filtra apenas as skills marcadas e envia para a ViewModel
                            val selectedSkills = skills.filter { it.checked }
                            onEvent(SkillsEvent.OnSaveSkills(selectedSkills))
                        }
                    )
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0f344e
)
@Composable
private fun SkillsContainerPreview() {
    XBizWorkTheme {
        SkillsContainer(
            modifier = Modifier.padding(10.dp),
            uiState = SkillUiState(
                categories = listOf(
                    CategoryResult(1, "Professor"),
                    CategoryResult(2, "Profissional de Saúde"),
                    CategoryResult(3, "Diarista"),
                    CategoryResult(4, "Manicure"),
                    CategoryResult(5, "Construção Civil"),
                    CategoryResult(6, "Jardinagem e Limpezas"),
                    CategoryResult(7, "Refrigeração"),
                    CategoryResult(8, "Eletricista"),
                    CategoryResult(9, "Educador Físico")
                )
            ),
            onEvent = {}
        )
    }
}
