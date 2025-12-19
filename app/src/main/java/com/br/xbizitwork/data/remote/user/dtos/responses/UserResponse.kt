package com.br.xbizitwork.data.remote.user.dtos.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response da API GET /user/{id}
 * Baseado na resposta REAL da API (não no schema Prisma completo)
 *
 * ATUALIZADO: Campos que a API NÃO retorna foram removidos:
 * - authId (não vem na resposta)
 * - status (não vem na resposta)
 * - registration (não vem na resposta)
 */
@Serializable
data class UserResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("cpf") val cpf: String?,
    @SerialName("dateOfBirth") val dateOfBirth: String?,
    @SerialName("gender") val gender: String?,
    @SerialName("zipCode") val zipCode: String?,
    @SerialName("address") val address: String?,
    @SerialName("number") val number: String?,
    @SerialName("neighborhood") val neighborhood: String?,
    @SerialName("city") val city: String?,
    @SerialName("state") val state: String?,
    @SerialName("mobilePhone") val mobilePhone: String?,
    @SerialName("latitude") val latitude: Float?,
    @SerialName("longitude") val longitude: Float?
)
