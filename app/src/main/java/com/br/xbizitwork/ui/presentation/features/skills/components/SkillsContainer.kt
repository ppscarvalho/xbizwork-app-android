package com.br.xbizitwork.ui.presentation.features.skills.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.features.skills.state.SkillUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun SkillsContainer(
    modifier: Modifier = Modifier,
    onSaveClick: (List<SkillUiState>) -> Unit
) {
    var skills by remember {
        mutableStateOf(
            listOf(
                SkillUiState(1, "Professor"),
                SkillUiState(2, "Profissional de Saúde"),
                SkillUiState(3, "Diarista"),
                SkillUiState(4, "Manicure"),
                SkillUiState(5, "Construção Civil"),
                SkillUiState(6, "Jardinagem e Limpezas"),
                SkillUiState(7, "Refrigeração"),
                SkillUiState(8, "Eletricista"),
                SkillUiState(9, "Educador Físico")
            )
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        SkillsCheckboxList(
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
            onClick = {
                onSaveClick(
                    skills.filter { it.checked }
                )
            },
            isLoading = false,
            contentColor = Color.White
        )
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onSaveClick = {}
        )
    }
}
