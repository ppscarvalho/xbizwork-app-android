package com.br.xbizitwork.ui.presentation.features.schedule.agenda.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.schedule.agenda.viewmodel.ProfessionalAgendaViewModel
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun ProfessionalAgendaScreen(
    onNavigateBack: () -> Unit,
    viewModel: ProfessionalAgendaViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Minha Agenda",
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> LoadingIndicator()
                else -> {
                    Column {
                        Text("Agenda do Profissional")
                        Text("Total de agendas: ${uiState.schedules.size}")
                        Text("Data selecionada: ${uiState.selectedDate}")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfessionalAgendaScreenPreview() {
    XBizWorkTheme {
        ProfessionalAgendaScreen(
            onNavigateBack = {}
        )
    }
    
}