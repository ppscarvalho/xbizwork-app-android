package com.br.xbizitwork.ui.presentation.features.searchprofessionals.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.R
import com.br.xbizitwork.ui.presentation.components.state.EmptyState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun SearchProfessionalEmptyState(
    modifier: Modifier = Modifier
) {
    EmptyState(
        image = painterResource(R.drawable.ic_empty_state_recipes),
        title = "Nenhum profissional encontrado",
        subTitle = {
            Text(
                text = "Tente buscar por outra habilidade",
                fontFamily = poppinsFontFamily,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = modifier.fillMaxSize()
    )
}

@Preview
@Composable
private fun SearchProfessionalEmptyStatePreview() {
    XBizWorkTheme{
        SearchProfessionalEmptyState()
    }
}
