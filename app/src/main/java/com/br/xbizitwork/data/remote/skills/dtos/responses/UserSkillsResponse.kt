package com.br.xbizitwork.data.remote.skills.dtos.responses

import com.br.xbizitwork.core.model.api.ApiResponse
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
 * Wrapper response para a lista de habilidades
 * O backend retorna um objeto com data, isSuccessful e message
 * Endpoint: GET /user-skills/user/{userId}
 * 
 * Exemplo:
 * {
 *   "data": [{ id, userId, categoryId, categoryDescription, createdAt }],
 *   "isSuccessful": true,
 *   "message": "Habilidades listadas com sucesso!"
 * }
 */
typealias UserSkillsResponse = ApiResponse<List<UserSkillItemResponse>>

