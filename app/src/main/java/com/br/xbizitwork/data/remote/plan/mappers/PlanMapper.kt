package com.br.xbizitwork.data.remote.plan.mappers

import com.br.xbizitwork.data.remote.plan.dtos.response.PlanResponse
import com.br.xbizitwork.domain.model.plan.PlanModel

fun PlanResponse.toDomain(): PlanModel {
    return PlanModel(
        id = id,
        description = description,
        price = price,
        duration = duration,
        isActive = isActive
    )
}