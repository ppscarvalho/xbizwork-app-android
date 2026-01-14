package com.br.xbizitwork.data.remote.skills.dtos.requests

import com.google.gson.annotations.SerializedName

/**
 * DTO para request de salvamento de habilidades
 * Será enviado para a API REST
 *
 * Endpoint: POST /user-skills
 */
data class SaveUserSkillsRequest(
    @SerializedName("categoryIds")
    val categoryIds: List<Int>
)

