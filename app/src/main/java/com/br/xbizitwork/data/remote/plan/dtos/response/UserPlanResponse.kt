package com.br.xbizitwork.data.remote.plan.dtos.response

import com.google.gson.annotations.SerializedName

data class UserPlanResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("userId")
    val userId: Int,

    @SerializedName("planId")
    val planId: Int,

    @SerializedName("startDate")
    val startDate: String,

    @SerializedName("expirationDate")
    val expirationDate: String,

    @SerializedName("isActive")
    val isActive: Boolean,

    @SerializedName("isExpired")
    val isExpired: Boolean,

    @SerializedName("remainingDays")
    val remainingDays: Int,

    @SerializedName("createdAt")
    val createdAt: String,

    @SerializedName("updatedAt")
    val updatedAt: String
)
