package com.br.xbizitwork.data.remote.plan.dtos.response

import com.google.gson.annotations.SerializedName

data class PlanResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("price")
    val price: Double,

    @SerializedName("durationInDays")
    val durationInDays: Int,

    @SerializedName("isActive")
    val isActive: Boolean,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String
)
