package com.br.xbizitwork.ui.presentation.features.plans.events

import com.br.xbizitwork.domain.model.plan.PlanModel

sealed class PlanEvent {
    data object OnRefresh : PlanEvent()
    data class OnPlanSelected(val plan: PlanModel) : PlanEvent()
    data class OnSubscribeClick(val userId: Int, val planId: Int) : PlanEvent()
    data object OnDismissSuccess : PlanEvent()
}
