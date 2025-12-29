package com.br.xbizitwork.ui.presentation.features.schedule.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun CleanButton(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onClearClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible
    ) {
        Icon(
            imageVector = Icons.Outlined.DeleteOutline,
            contentDescription = "Icone Delete",
            modifier = modifier
                .clickable(
                onClick = onClearClick
            ),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CleanButtonLightPreview() {
    XBizWorkTheme{
        CleanButton(
            isVisible = true,
            onClearClick = {}
        )
    }
}