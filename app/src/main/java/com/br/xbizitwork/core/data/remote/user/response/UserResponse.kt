package com.br.xbizitwork.core.data.remote.user.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("cpf")
    val cpf: String?,

    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,

    @SerializedName("gender")
    val gender: String?,

    @SerializedName("email")
    val email: String,

    @SerializedName("zipCode")
    val zipCode: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("number")
    val number: String?,

    @SerializedName("neighborhood")
    val neighborhood: String?,

    @SerializedName("city")
    val city: String?,

    @SerializedName("state")
    val state: String?,

    @SerializedName("mobilePhone")
    val mobilePhone: String?,

    @SerializedName("latitude")
    val latitude: String?,

    @SerializedName("longitude")
    val longitude: String?,
)