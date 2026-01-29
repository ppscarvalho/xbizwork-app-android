package com.br.xbizitwork.domain.model.plan

data class PlanModel(
    val id: Int,
    val description: String,
    val price: Double,
    val duration: Int,
    val isActive: Boolean
)
