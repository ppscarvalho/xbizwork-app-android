package com.br.xbizitwork.ui.presentation.components.cards

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.xbizitwork.R

@Composable
fun CardContainer(
    modifier: Modifier = Modifier,
    onNavigateToSignScreen: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ShortcutCard(
            icon = Icons.Default.Share,
            title = stringResource(id = R.string.divulgue_text),
            subtitle = stringResource(id = R.string.seu_trabalho_text),
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            onClick = { onNavigateToSignScreen() },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        ShortcutCard(
            icon = Icons.Default.Groups,
            title = stringResource(id = R.string.indique_text),
            subtitle = stringResource(id = R.string.profissional_text),
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onClick = { },
            modifier = Modifier.weight(1f)
        )
    }
}