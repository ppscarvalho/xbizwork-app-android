package com.br.xbizitwork.data.remote.plan.dtos.response

import com.google.gson.annotations.SerializedName

data class PlanResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("duration")
    val duration: Int,

    @SerializedName("isActive")
    val isActive: Boolean
)
