package com.br.xbizitwork.ui.presentation.features.plans.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.presentation.components.dialogs.PlanSelectionDialog
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.plans.events.PlanEvent
import com.br.xbizitwork.ui.presentation.features.plans.state.PlanUiState

@Composable
fun PlanContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    uiState: PlanUiState,
    onEvent: (PlanEvent) -> Unit,
    onNavigateToLogin: () -> Unit = {},
    onNavigateToSignUp: () -> Unit = {}
) {
    var showAuthDialog by remember { mutableStateOf(false) }

    AppGradientBackground(
        modifier = modifier,
        paddingValues = paddingValues
    ) {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator(message = "Carregando planos...")
                }
            }

            uiState.errorMessage != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = uiState.errorMessage)
                }
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    uiState.plans.forEach { plan ->
                        val benefits = plan.getBenefits()
                        val isCurrentPlan = uiState.currentUserPlan?.planId == plan.id


                        PlanCard(
                            planName = plan.name,
                            benefits = benefits,
                            price = "R$ ${plan.price}",
                            duration = "${plan.durationInDays} dias",
                            isLoading = uiState.isSubscribing,
                            buttonEnabled = !uiState.isSubscribing && plan.isActive && !isCurrentPlan,
                            isCurrentPlan = isCurrentPlan,
                            onClick = {
                                // Verificar autenticação antes de assinar
                                if (!uiState.isAuthenticated) {
                                    showAuthDialog = true
                                } else {
                                    onEvent(
                                        PlanEvent.OnSubscribeClick(
                                            userId = uiState.currentUserId,
                                            planId = plan.id
                                        )
                                    )
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }

    // Dialog de autenticação
    if (showAuthDialog) {
        PlanSelectionDialog(
            onDismiss = { showAuthDialog = false },
            onLoginClick = {
                showAuthDialog = false
                onNavigateToLogin()
            },
            onSignUpClick = {
                showAuthDialog = false
                onNavigateToSignUp()
            }
        )
    }
}
