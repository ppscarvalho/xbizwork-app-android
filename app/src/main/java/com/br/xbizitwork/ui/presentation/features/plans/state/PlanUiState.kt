package com.br.xbizitwork.ui.presentation.features.plans.state

import com.br.xbizitwork.domain.model.plan.PlanModel
import com.br.xbizitwork.domain.model.plan.UserPlanModel

data class PlanUiState(
    val plans: List<PlanModel> = emptyList(),
    val selectedPlan: PlanModel? = null,
    val currentUserPlan: UserPlanModel? = null,
    val isLoading: Boolean = false,
    val isLoadingCurrentPlan: Boolean = false,
    val isSubscribing: Boolean = false,
    val errorMessage: String? = null,
    val subscriptionSuccess: Boolean = false,
    val subscribedPlanId: Int? = null,
    val isAuthenticated: Boolean = false,
    val currentUserId: Int = 0
)
