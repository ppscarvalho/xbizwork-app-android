package com.br.xbizitwork.ui.presentation.features.schedule.list.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.presentation.components.state.EmptyState
import com.br.xbizitwork.ui.presentation.components.state.ErrorState
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.schedule.list.events.ViewSchedulesEvent
import com.br.xbizitwork.ui.presentation.features.schedule.list.state.ViewSchedulesUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.example.xbizitwork.R

@Composable
fun ListSchedulesContent(
    uiState: ViewSchedulesUIState,
    onEvent: (ViewSchedulesEvent) -> Unit,
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
                    onRetry = { onEvent(ViewSchedulesEvent.OnRefresh) }
                )
            }

            uiState.isEmpty -> {
                EmptyState(
                    image = painterResource(R.drawable.ic_empty_state_recipes),
                    title = "Nenhuma agenda criada"
                )
            }

            else -> {
                ListSchedulesContainer(
                    uiState = uiState
                )
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ListSchedulesContentPreview() {
    XBizWorkTheme {
        ListSchedulesContent(
            uiState = ViewSchedulesUIState(isLoading = false),
            onEvent = {},
            paddingValues = PaddingValues()
        )
    }
}
