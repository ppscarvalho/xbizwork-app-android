package com.br.xbizitwork.ui.presentation.features.searchprofessionals.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.components.state.ErrorState
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.state.SearchProfessionalsUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
// Imports do Maps comentados - mudança de escopo
//import com.google.android.gms.maps.model.LatLng
//import com.google.maps.android.compose.Marker
//import com.google.maps.android.compose.MarkerState

/**
 * Container for professional search results
 * Following the same pattern as SearchScheduleContainer
 */
@Composable
fun SearchProfessionalsContainer(
    modifier: Modifier = Modifier,
    uiState: SearchProfessionalsUiState,
    onEvent: (SearchProfessionalBySkillEvent) -> Unit,
    onProfessionalSelected: (ProfessionalSearchBySkill) -> Unit = {},
    onMapClick: (ProfessionalSearchBySkill) -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        when {
            uiState.isLoading -> {
                LoadingIndicator(
                    message = "Buscando profissionais...",
                    modifier = Modifier.padding(top = 10.dp)
                )
            }

            uiState.errorMessage != null -> {
                ErrorState(
                    message = uiState.errorMessage,
                    onRetry = { onEvent(SearchProfessionalBySkillEvent.OnRefresh) }
                )
            }

            uiState.isEmpty -> {
                SearchProfessionalEmptyState(modifier = modifier.padding(12.dp))
            }

            uiState.professionals.isNotEmpty() -> {
                // Lista de profissionais usando ProfessionalCard
                ProfessionalsList(
                    professionals = uiState.professionals,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    onProfessionalClick = { professional ->
                        onProfessionalSelected(professional)
                    },
                    onMapClick = onMapClick
                )

                // TODO: Adicionar link para visualizar no mapa
                // Mapa ocupando toda a área disponível (COMENTADO - mudança de escopo)
//                ProfessionalsMapView(
//                    professionals = uiState.professionals,
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 12.dp),
//                    onProfessionalClick = { professional ->
//                        onProfessionalSelected(professional)
//                    }
//                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFF0f344e
)
@Composable
private fun SearchProfessionalsContainerPreview() {
    XBizWorkTheme {
        SearchProfessionalsContainer(
            modifier = Modifier.padding(16.dp),
            uiState = SearchProfessionalsUiState(
                professionals = listOf(),
                isLoading = true,
                errorMessage = null,
                isEmpty = false,
                searchIsEmpty = false
            ),
            onEvent = {}
        )
    }
}
