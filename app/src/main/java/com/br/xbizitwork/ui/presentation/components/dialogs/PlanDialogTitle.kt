package com.br.xbizitwork.ui.presentation.components.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.R
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Título do diálogo de seleção de plano
 */
@Composable
fun PlanDialogTitle() {
    Text(
        text = stringResource(id = R.string.possui_cadastro_text),
        fontFamily = poppinsFontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}
