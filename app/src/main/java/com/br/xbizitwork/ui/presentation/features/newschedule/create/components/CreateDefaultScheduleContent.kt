package com.br.xbizitwork.ui.presentation.features.newschedule.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.features.newschedule.create.events.CreateDefaultScheduleEvent
import com.br.xbizitwork.ui.presentation.features.newschedule.create.state.CreateDefaultScheduleUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun CreateDefaultScheduleContent(
    paddingValues: PaddingValues,
    uiState: CreateDefaultScheduleUIState,
    onEvent: (CreateDefaultScheduleEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        CreateDefaultScheduleContainer(
            uiState = uiState,
            onEvent = onEvent
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun CreateDefaultScheduleContentPreview() {
    XBizWorkTheme{
        CreateDefaultScheduleContent(
            paddingValues = PaddingValues(0.dp),
            uiState = CreateDefaultScheduleUIState(),
            onEvent = {}
        )
    }
}