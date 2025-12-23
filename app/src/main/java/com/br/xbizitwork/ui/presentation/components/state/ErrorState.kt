package com.br.xbizitwork.ui.presentation.components.state

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.example.xbizitwork.R

@Composable
fun ErrorState(
    modifier: Modifier = Modifier,
    message: String?,
    onRetry: (() -> Unit)? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(12.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_error_state),
            contentDescription = null,
            modifier = modifier.size(250.dp)
        )
        Spacer(Modifier.height(12.dp))

        val poppinsFontFamily = null
        Text(
            text = stringResource(R.string.am_error_occurred_text),
            fontFamily = poppinsFontFamily,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(12.dp))

        message?.let {
            Text(
                text = it,
                fontFamily = poppinsFontFamily,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.height(24.dp))
        onRetry?.let {
            Button(
                onClick = it,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                )
            ) {
                Text(
                    text = stringResource(R.string.try_again_text),
                    fontFamily = poppinsFontFamily,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorStatePreview() {
    XBizWorkTheme {
        ErrorState(
            message = "Sem conexão com a internet",
            onRetry = {}
        )
    }
}

@Preview
@Composable
private fun ErrorStateRetryPreview() {
    XBizWorkTheme {
        ErrorState(
            message = "Sem conexão com a internet",
            onRetry = null
        )
    }
}