package com.br.xbizitwork.data.remote.plan.mappers

import com.br.xbizitwork.data.remote.plan.dtos.response.PlanResponse
import com.br.xbizitwork.data.remote.plan.dtos.response.UserPlanResponse
import com.br.xbizitwork.domain.model.plan.PlanModel
import com.br.xbizitwork.domain.model.plan.UserPlanModel

fun PlanResponse.toDomain(): PlanModel {
    return PlanModel(
        id = id,
        name = name,
        description = description,
        price = price,
        durationInDays = durationInDays,
        isActive = isActive,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun UserPlanResponse.toDomain(): UserPlanModel {
    return UserPlanModel(
        id = id,
        userId = userId,
        planId = planId,
        startDate = startDate,
        expirationDate = expirationDate,
        isActive = isActive,
        isExpired = isExpired,
        remainingDays = remainingDays,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}