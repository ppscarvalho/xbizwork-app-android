package com.br.xbizitwork.domain.model.user

import java.time.LocalDate

/**
 * Model completo do usuário vindo da API
 * Baseado no schema Prisma do backend
 */
data class UserModel(
    val id: Int,
    val authId: Int?,
    val name: String,
    val cpf: String?,
    val dateOfBirth: LocalDate?,
    val gender: String?,
    val zipCode: String?,
    val address: String?,
    val number: String?,
    val neighborhood: String?,
    val city: String?,
    val state: String?,
    val mobilePhone: String?,
    val status: Boolean,
    val registration: Boolean,
    val latitude: Float?,
    val longitude: Float?
)

