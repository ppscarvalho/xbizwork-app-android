package com.br.xbizitwork.ui.presentation.features.schedule.list.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.presentation.components.state.ErrorState
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.schedule.list.events.ListSchedulesEvent
import com.br.xbizitwork.ui.presentation.features.schedule.list.state.ListSchedulesUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun ListSchedulesContent(
    uiState: ListSchedulesUIState,
    onEvent: (ListSchedulesEvent) -> Unit,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        when {
            uiState.isLoading -> {
                LoadingIndicator()
            }

            uiState.errorMessage != null -> {
                ErrorState(
                    message = uiState.errorMessage,
                    onRetry = { onEvent(ListSchedulesEvent.OnRefresh) }
                )
            }

            uiState.isEmpty -> {
                ListScheduleEmptyState()
            }

            else -> {
                ListSchedulesContainer(
                    uiState = uiState
                )
            }
        }
    }
}
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ListSchedulesContentLightPreview() {
    XBizWorkTheme {
        ListSchedulesContent(
            uiState = ListSchedulesUIState(isLoading = false),
            onEvent = {},
            paddingValues = PaddingValues()
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListSchedulesContentDarkPreview() {
    XBizWorkTheme {
        ListSchedulesContent(
            uiState = ListSchedulesUIState(isLoading = false),
            onEvent = {},
            paddingValues = PaddingValues()
        )
    }
}
