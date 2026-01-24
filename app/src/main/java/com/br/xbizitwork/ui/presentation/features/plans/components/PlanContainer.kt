package com.br.xbizitwork.ui.presentation.features.plans.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun PlanContainer(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    isLoading: Boolean,
    buttonEnabled: Boolean,
    onClick: () -> Unit,
) {
    AppGradientBackground(
        modifier = modifier,
        paddingValues = paddingValues
    ){
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PlanCard(
                planLabel = "Plano Básico",
                planDescription = "Acesso às funcionalidades essenciais do aplicativo",
                isLoading = isLoading,
                buttonEnabled = buttonEnabled,
                onClick = onClick
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            PlanCard(
                planLabel = "Plano Médio",
                planDescription = "Acesso às funcionalidades essenciais do aplicativo",
                isLoading = isLoading,
                buttonEnabled = buttonEnabled,
                onClick = onClick
            )
            Spacer(modifier = Modifier.height(16.dp))

            PlanCard(
                planLabel = "Plano Premiun",
                planDescription = "Acesso às funcionalidades essenciais do aplicativo",
                isLoading = isLoading,
                buttonEnabled = buttonEnabled,
                onClick = onClick
            )
        }
    }
}

@Preview
@Composable
private fun PlanContainerPreview() {
    XBizWorkTheme{
        PlanContainer(
            paddingValues = PaddingValues(8.dp),
            isLoading = false,
            buttonEnabled = true,
            onClick = {}
        )
    }
}
