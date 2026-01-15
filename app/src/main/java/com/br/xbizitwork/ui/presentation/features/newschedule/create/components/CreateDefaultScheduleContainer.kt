package com.br.xbizitwork.ui.presentation.features.newschedule.create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.features.newschedule.create.events.CreateDefaultScheduleEvent
import com.br.xbizitwork.ui.presentation.features.newschedule.create.state.CreateDefaultScheduleUIState
import com.br.xbizitwork.ui.theme.BeigeBackground
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun CreateDefaultScheduleContainer(
    uiState: CreateDefaultScheduleUIState,
    onEvent: (CreateDefaultScheduleEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        // Card visual com dias e horário
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = BeigeBackground,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "Dias",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp,
                    )
                    Text(
                        uiState.daysDescription,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFontFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        "Horário",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp,
                    )
                    Text(
                        "${uiState.startTime} às ${uiState.endTime}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        fontFamily = poppinsFontFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Criar agenda padrão",
            enabled = !uiState.isLoading,
            isLoading = uiState.isLoading,
            onClick = {
                onEvent(CreateDefaultScheduleEvent.OnCreateDefaultSchedule)
            },
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}


@Preview (showBackground = true)
@Composable
private fun CreateDefaultScheduleContainerPreview() {
    XBizWorkTheme{
        CreateDefaultScheduleContainer(
            uiState = CreateDefaultScheduleUIState(
                daysDescription = "Segunda a sexta-feira",
                startTime = "09:00",
                endTime = "18:00",
                isLoading = false
            ),
            onEvent = {}
        )
    }
}
