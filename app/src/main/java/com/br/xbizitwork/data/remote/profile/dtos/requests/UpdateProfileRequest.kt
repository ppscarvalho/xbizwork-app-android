package com.br.xbizitwork.data.remote.profile.dtos.requests

import com.google.gson.annotations.SerializedName

/**
 * DTO para request de atualização de perfil
 * Será enviado para a API REST
 *
 * ATUALIZADO: Agora inclui TODOS os campos editáveis do schema Prisma + id
 * Os @SerializedName devem corresponder aos campos esperados pela API
 *
 * Endpoint: PUT /users/{id}
 */
data class UpdateProfileRequest(
    // ID do usuário (necessário para o endpoint PUT /users/{id})
    @SerializedName("id")
    val id: Int,

    // Dados básicos
    @SerializedName("name")
    val name: String,

    @SerializedName("cpf")
    val cpf: String?,

    @SerializedName("dateOfBirth")
    val dateOfBirth: String?, // ISO String: "YYYY-MM-DD"

    @SerializedName("gender")
    val gender: String?,

    // Contato
    @SerializedName("email")
    val email: String,

    @SerializedName("mobilePhone")
    val mobilePhone: String?,

    // Endereço
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

    // Localização
    @SerializedName("latitude")
    val latitude: Double?,

    @SerializedName("longitude")
    val longitude: Double?
)
