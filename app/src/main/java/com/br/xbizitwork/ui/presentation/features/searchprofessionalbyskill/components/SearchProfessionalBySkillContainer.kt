package com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.state.SearchProfessionalBySkillUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Container for professional search results
 * Following the same pattern as SkillsContainer
 */
@Composable
fun SearchProfessionalBySkillContainer(
    modifier: Modifier = Modifier,
    uiState: SearchProfessionalBySkillUIState
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> {
                LoadingIndicator(
                    message = "Buscando profissionais..."
                )
            }
            uiState.errorMessage != null -> {
                Text(
                    text = uiState.errorMessage,
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
            !uiState.hasSearched -> {
                Text(
                    text = "Digite uma habilidade e clique em buscar\npara encontrar profissionais",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
            uiState.professionals.isEmpty() -> {
                Text(
                    text = "Nenhum profissional encontrado",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.professionals) { professional ->
                        ProfessionalCard(professional = professional)
                    }
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
private fun SearchProfessionalBySkillContainerPreview() {
    XBizWorkTheme {
        SearchProfessionalBySkillContainer(
            modifier = Modifier.padding(16.dp),
            uiState = SearchProfessionalBySkillUIState(
                professionals = listOf(
                    ProfessionalSearchBySkill(
                        id = 13,
                        name = "Pedro Carvalho",
                        mobilePhone = "91992511848",
                        city = "Belém",
                        state = "PA",
                        skill = SkillInfo(9, "Educador Físico")
                    ),
                    ProfessionalSearchBySkill(
                        id = 14,
                        name = "Paula Manuela",
                        mobilePhone = "Não informado",
                        city = "Não informado",
                        state = "Não informado",
                        skill = SkillInfo(9, "Educador Físico")
                    )
                ),
                hasSearched = true
            )
        )
    }
}
