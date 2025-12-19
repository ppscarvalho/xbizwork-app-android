package com.br.xbizitwork.data.remote.auth.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO que representa o objeto "data" retornado pela API
 * Contém as informações do usuário autenticado
 */
data class UserData(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("token")
    val token: String
)
