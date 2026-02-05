package com.br.xbizitwork.domain.model.plan

data class UserPlanModel(
    val id: Int,
    val userId: Int,
    val planId: Int,
    val startDate: String,
    val expirationDate: String,
    val isActive: Boolean,
    val isExpired: Boolean,
    val remainingDays: Int,
    val createdAt: String,
    val updatedAt: String
)
