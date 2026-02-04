package com.br.xbizitwork.data.remote.plan.dtos.request

import com.google.gson.annotations.SerializedName

data class SubscribePlanRequest(
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("planId")
    val planId: Int
)
