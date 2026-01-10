package com.br.xbizitwork.domain.model.profile

import java.time.LocalDate

/**
 * Modelo de domínio para atualização de perfil
 * Representa os dados que serão enviados para atualizar o perfil do usuário
 *
 * ATUALIZADO: Agora inclui TODOS os campos editáveis do schema Prisma:
 * - ID do usuário (necessário para endpoint PUT /users/{id})
 * - Dados básicos: name, cpf, dateOfBirth, gender
 * - Contato: email, mobilePhone
 * - Endereço: zipCode, address, number, neighborhood, city, state
 * - Localização: latitude, longitude
 */
data class UpdateProfileRequestModel(
    // ID do usuário
    val id: Int,

    // Dados básicos
    val name: String,
    val cpf: String?,
    val dateOfBirth: LocalDate?,
    //val gender: String?,

    // Contato
    val email: String,
    val mobilePhone: String?,

    // Endereço
    val zipCode: String?,
    val address: String?,
    val number: String?,
    val neighborhood: String?,
    val city: String?,
    val state: String?,

    // Localização
    val latitude: Double?,
    val longitude: Double?
)
