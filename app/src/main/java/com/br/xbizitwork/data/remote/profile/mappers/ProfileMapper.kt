package com.br.xbizitwork.data.remote.profile.mappers

import com.br.xbizitwork.data.remote.profile.dtos.requests.UpdateProfileRequest
import com.br.xbizitwork.domain.model.profile.UpdateProfileRequestModel

/**
 * Extensão para converter domain model para DTO de request
 * ATUALIZADO: Agora mapeia TODOS os campos do schema Prisma + id + localização
 */
fun UpdateProfileRequestModel.toRequest(): UpdateProfileRequest {
    return UpdateProfileRequest(
        // ID do usuário
        id = this.id,

        // Dados básicos
        name = this.name,
        cpf = this.cpf,
        dateOfBirth = this.dateOfBirth?.toString(), // LocalDate → String ISO
        gender = this.gender,

        // Contato
        email = this.email,
        mobilePhone = this.mobilePhone,

        // Endereço
        zipCode = this.zipCode,
        address = this.address,
        number = this.number,
        neighborhood = this.neighborhood,
        city = this.city,
        state = this.state,

        // Localização
        latitude = this.latitude,
        longitude = this.longitude
    )
}

