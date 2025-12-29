package com.br.xbizitwork.ui.presentation.features.schedule.search.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.schedule.search.components.SearchScheduleBar
import com.br.xbizitwork.ui.presentation.features.schedule.search.components.SearchScheduleContent
import com.br.xbizitwork.ui.presentation.features.schedule.search.events.SearchSchedulesEvent
import com.br.xbizitwork.ui.presentation.features.schedule.search.state.SearchSchedulesUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun SearchScheduleScreen(
    modifier: Modifier = Modifier,
    uiState: SearchSchedulesUIState,
    onNavigateBack: () -> Unit = {},
    onNavigateToScheduleDetail: (String) -> Unit,
    onEvent: (SearchSchedulesEvent) -> Unit
    ) {

    LaunchedEffect(Unit) {
        onEvent(SearchSchedulesEvent.OnObserverSearch)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Pesquisar Profissional",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        content = { paddingValues ->
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
            ){
                SearchScheduleBar(
                    queryTextState = uiState.queryTextState,
                    modifier = modifier.padding(top = 10.dp)
                )
                SearchScheduleContent(
                    uiState = uiState,
                    onEvent = onEvent,
                    onNavigateToScheduleDetail = onNavigateToScheduleDetail
                )
            }
        }
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchScheduleScreenPreview() {
    XBizWorkTheme{
        SearchScheduleScreen(
            uiState = SearchSchedulesUIState(
                errorMessage = "Ocorreu um erro na pesquisa"
            ),
            onNavigateBack = {},
            onNavigateToScheduleDetail = {},
            onEvent = {}
        )
    }
}