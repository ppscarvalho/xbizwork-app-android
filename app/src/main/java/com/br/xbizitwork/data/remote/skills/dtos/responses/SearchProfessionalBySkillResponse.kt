package com.br.xbizitwork.data.remote.skills.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO para um profissional retornado na busca por habilidade
 * Representa os dados de um profissional encontrado pelo backend
 */
data class ProfessionalSkillItemResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("mobilePhone")
    val mobilePhone: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("state")
    val state: String,

    @SerializedName("skill")
    val skill: SkillInfoResponse
)

/**
 * DTO para informações da habilidade associada ao profissional
 */
data class SkillInfoResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("description")
    val description: String
)

/**
 * DTO para a resposta completa da busca de profissionais por habilidade
 * Endpoint: GET /api/v1/user-skills/search?skill={termo}
 */
data class SearchProfessionalBySkillResponse(
    @SerializedName("data")
    val data: List<ProfessionalSkillItemResponse>,

    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,

    @SerializedName("message")
    val message: String
)
