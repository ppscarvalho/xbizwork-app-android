package com.br.xbizitwork.data.remote.skills.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO para um item de habilidade do usuário
 * Representa uma habilidade individual retornada pelo backend
 */
data class UserSkillItemResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("userId")
    val userId: Int,

    @SerializedName("categoryId")
    val categoryId: Int,

    @SerializedName("categoryDescription")
    val categoryDescription: String,

    @SerializedName("createdAt")
    val createdAt: String
)

/**
 * Type alias para a lista de habilidades
 * O backend retorna um array de objetos
 * Endpoint: GET /user-skills/user/{userId}
 */
typealias UserSkillsResponse = List<UserSkillItemResponse>

