package com.br.xbizitwork.data.remote.cep.mappers

import com.br.xbizitwork.data.remote.cep.dtos.responses.CepResponse
import com.br.xbizitwork.domain.model.cep.CepModel

/**
 * Converte CepResponse (DTO) para CepModel (Domain)
 *
 * API retorna:
 * {
 *   "zipCode": "66815-005",
 *   "address": "Passagem Apiraíba",
 *   "neighborhood": "Maracacuera (Icoaraci)",
 *   "city": "Belém",
 *   "state": "PA"
 * }
 */
fun CepResponse.toModel(): CepModel {
    return CepModel(
        cep = this.zipCode ?: "",
        logradouro = this.address ?: "",
        bairro = this.neighborhood ?: "",
        cidade = this.city ?: "",
        estado = this.state ?: ""
    )
}

