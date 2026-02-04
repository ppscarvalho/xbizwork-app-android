package com.br.xbizitwork.ui.presentation.features.plans.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.usecase.plan.GetAllPublicPlanUseCase
import com.br.xbizitwork.domain.usecase.plan.GetUserCurrentPlanUseCase
import com.br.xbizitwork.domain.usecase.plan.SubscribeToPlanUseCase
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCase
import com.br.xbizitwork.ui.presentation.features.plans.events.PlanEvent
import com.br.xbizitwork.ui.presentation.features.plans.state.PlanUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val getAllPublicPlanUseCase: GetAllPublicPlanUseCase,
    private val getAuthSessionUseCase: GetAuthSessionUseCase,
    private val getUserCurrentPlanUseCase: GetUserCurrentPlanUseCase,
    private val subscribeToPlanUseCase: SubscribeToPlanUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlanUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffectChannel = Channel<AppSideEffect>(capacity = Channel.BUFFERED)
    val sideEffectChannel = _sideEffectChannel.receiveAsFlow()

    init {
        loadPlans()
        observeAuthSession()
    }

    fun onEvent(event: PlanEvent) {
        when (event) {
            is PlanEvent.OnRefresh -> loadPlans()
            is PlanEvent.OnPlanSelected -> selectPlan(event.plan)
            is PlanEvent.OnSubscribeClick -> subscribeToPlan(event.userId, event.planId)
            is PlanEvent.OnDismissSuccess -> dismissSuccess()
        }
    }

    private fun observeAuthSession() {
        viewModelScope.launch {
            getAuthSessionUseCase.invoke().collect { authSession ->
                _uiState.update {
                    it.copy(
                        isAuthenticated = authSession.token.isNotEmpty(),
                        currentUserId = authSession.id
                    )
                }

                // Quando estiver autenticado, buscar plano atual (via token JWT)
                if (authSession.token.isNotEmpty()) {
                    loadUserCurrentPlan()
                }
            }
        }
    }

    private fun loadUserCurrentPlan() {
        viewModelScope.launch {
            logInfo("PLAN_VM", "🔍 Iniciando loadUserCurrentPlan...")
            getUserCurrentPlanUseCase.invoke().collectUiState(
                onLoading = {
                    logInfo("PLAN_VM", "⏳ Carregando plano atual...")
                    _uiState.update { it.copy(isLoadingCurrentPlan = true) }
                },
                onSuccess = { userPlan ->
                    logInfo("PLAN_VM", "✅ Plano atual do usuário: ${userPlan?.planId ?: "nenhum"}")
                    logInfo("PLAN_VM", "📋 UserPlan completo: $userPlan")
                    _uiState.update {
                        it.copy(
                            currentUserPlan = userPlan,
                            isLoadingCurrentPlan = false
                        )
                    }
                },
                onFailure = { error ->
                    logInfo("PLAN_VM", "❌ Erro ao buscar plano atual: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoadingCurrentPlan = false
                        )
                    }
                }
            )
        }
    }

    private fun loadPlans() {
        viewModelScope.launch {
            getAllPublicPlanUseCase.invoke().collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { plans ->
                    logInfo("PLAN_VM", "✅ ${plans.size} planos carregados")
                    _uiState.update {
                        it.copy(
                            plans = plans,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    logInfo("PLAN_VM", "❌ Erro ao carregar planos: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
            )
        }
    }

    private fun selectPlan(plan: com.br.xbizitwork.domain.model.plan.PlanModel) {
        _uiState.update { it.copy(selectedPlan = plan) }
    }

    private fun subscribeToPlan(userId: Int, planId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSubscribing = true) }

            val parameters = SubscribeToPlanUseCase.Parameters(userId = userId, planId = planId)
            subscribeToPlanUseCase.invoke(parameters).collectUiState(
                onLoading = {
                    logInfo("PLAN_VM", "⏳ Assinando plano...")
                },
                onSuccess = { subscription ->
                    logInfo("PLAN_VM", "✅ Plano assinado com sucesso: ${subscription.id}")
                    _uiState.update {
                        it.copy(
                            isSubscribing = false,
                            subscriptionSuccess = true,
                            subscribedPlanId = planId
                        )
                    }
                    _sideEffectChannel.send(
                        AppSideEffect.ShowToast("Plano assinado com sucesso!")
                    )

                    // Recarregar plano atual do usuário (via token JWT)
                    loadUserCurrentPlan()
                },
                onFailure = { error ->
                    logInfo("PLAN_VM", "❌ Erro ao assinar plano: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isSubscribing = false,
                            errorMessage = error.message
                        )
                    }
                    _sideEffectChannel.send(
                        AppSideEffect.ShowToast("Erro ao assinar plano: ${error.message}")
                    )
                }
            )
        }
    }

    private fun dismissSuccess() {
        _uiState.update {
            it.copy(
                subscriptionSuccess = false,
                subscribedPlanId = null
            )
        }
    }
}
