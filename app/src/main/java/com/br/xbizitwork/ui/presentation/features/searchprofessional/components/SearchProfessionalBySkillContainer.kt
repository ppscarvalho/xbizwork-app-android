package com.br.xbizitwork.ui.presentation.features.searchprofessional.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.domain.model.skills.ProfessionalSearchResult
import com.br.xbizitwork.domain.model.skills.SkillInfo
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.searchprofessional.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessional.state.SearchProfessionalBySkillUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Container component - state observation and event binding
 */
@Composable
fun SearchProfessionalBySkillContainer(
    modifier: Modifier = Modifier,
    uiState: SearchProfessionalBySkillUIState,
    onEvent: (SearchProfessionalBySkillEvent) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Search Input
        OutlinedTextField(
            value = uiState.searchTerm,
            onValueChange = { onEvent(SearchProfessionalBySkillEvent.OnSearchTermChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Ex: pedreiro, eletricista...",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp
                )
            },
            trailingIcon = {
                if (uiState.searchTerm.isNotEmpty()) {
                    IconButton(onClick = { onEvent(SearchProfessionalBySkillEvent.OnClearSearch) }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Limpar busca"
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            ),
            singleLine = true
        )

        // Search Button
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            text = "Buscar",
            enabled = uiState.searchTerm.isNotEmpty() && !uiState.isLoading,
            isLoading = uiState.isLoading,
            contentColor = Color.White,
            onClick = { onEvent(SearchProfessionalBySkillEvent.OnSearch) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            }
        )

        // Results Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = false)
        ) {
            when {
                uiState.isLoading -> {
                    LoadingIndicator(
                        message = "Buscando profissionais..."
                    )
                }
                uiState.isEmpty -> {
                    EmptyStateMessage()
                }
                uiState.errorMessage != null -> {
                    ErrorMessage(uiState.errorMessage)
                }
                uiState.professionals.isNotEmpty() -> {
                    ProfessionalsList(
                        professionals = uiState.professionals
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfessionalsList(
    professionals: List<ProfessionalSearchResult>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(professionals) { professional ->
            ProfessionalCard(professional = professional)
        }
    }
}

@Composable
private fun ProfessionalCard(
    professional: ProfessionalSearchResult
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Nome
            Text(
                text = professional.name,
                fontFamily = poppinsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Habilidade
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Habilidade:",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = professional.skill.description,
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Telefone
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Telefone:",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = professional.mobilePhone,
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Localização
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Localização:",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${professional.city} - ${professional.state}",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun EmptyStateMessage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Nenhum profissional encontrado",
            fontFamily = poppinsFontFamily,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            fontFamily = poppinsFontFamily,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SearchProfessionalBySkillContainerPreview() {
    XBizWorkTheme {
        SearchProfessionalBySkillContainer(
            uiState = SearchProfessionalBySkillUIState(
                searchTerm = "pedreiro",
                professionals = listOf(
                    ProfessionalSearchResult(
                        id = 1,
                        name = "João da Silva",
                        mobilePhone = "91992511848",
                        city = "Belém",
                        state = "PA",
                        skill = SkillInfo(5, "Pedreiro")
                    ),
                    ProfessionalSearchResult(
                        id = 2,
                        name = "Maria Santos",
                        mobilePhone = "91991234567",
                        city = "Ananindeua",
                        state = "PA",
                        skill = SkillInfo(5, "Pedreiro")
                    )
                )
            ),
            onEvent = {}
        )
    }
}
