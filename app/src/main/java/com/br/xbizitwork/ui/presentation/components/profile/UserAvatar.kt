package com.br.xbizitwork.ui.presentation.components.profile

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.core.util.extensions.getInitials
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun UserAvatar(
    modifier: Modifier = Modifier,
    userName: String,
    fontSize: TextUnit = 36.sp,
    fontWeight: FontWeight = FontWeight.Bold
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        Text(
            text = userName.getInitials(),
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun UserAvatarPreview() {
    XBizWorkTheme {
        UserAvatar(
            userName ="Pedro Carvalho"
        )
    }
}