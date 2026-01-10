package com.br.xbizitwork.ui.presentation.features.newschedule.success.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ScheduleSuccessContent(
    paddingValues: PaddingValues,
    onConfirm: () -> Unit,
    onEditSchedule: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Parte principal (container)
        ScheduleSuccessContainer()

        Spacer(modifier = Modifier.height(32.dp))

        // Particularidades do fluxo (ficam no content)
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onConfirm
        ) {
            Text("OK")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onEditSchedule) {
            Text("Ajustar agenda")
        }
    }
}
