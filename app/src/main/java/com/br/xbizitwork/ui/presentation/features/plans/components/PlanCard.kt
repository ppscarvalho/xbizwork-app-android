package com.br.xbizitwork.ui.presentation.features.plans.components

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.R
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.theme.BeigeBackground
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun PlanCard(
    modifier: Modifier = Modifier,
    planLabel: String = "Plano",
    planDescription: String = "Descrição do plano",
    isLoading: Boolean,
    buttonEnabled: Boolean,
    onClick: () -> Unit,
    ) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = BeigeBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                PlanInfoRow(
                    modifier = modifier,
                    planLabel = planLabel,
                    planDescription = planDescription
                )
            }
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .height(45.dp)
                    .shadow(5.dp, RoundedCornerShape(25.dp)),
                text = "Assinar",
                contentColor = Color.White,
                enabled = buttonEnabled,
                onClick = onClick,
                isLoading = isLoading,
            )
        }
    }
}

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun PlanCardPreview() {
    XBizWorkTheme{
        PlanCard(
            modifier = Modifier.padding(2.dp),
            planLabel = "Plano Básico",
            planDescription = "Acesso às funcionalidades essenciais do aplicativo",
            isLoading = false,
            buttonEnabled = true,
            onClick = {}
        )
    }
}
