package com.br.xbizitwork.data.remote.cep.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO Response da API de CEP
 * Endpoint: GET /api/v1/cep/{cep}
 *
 * ✅ CAMPOS CORRETOS baseados na resposta real da API:
 * {
 *   "address": "Passagem Apiraíba",
 *   "neighborhood": "Maracacuera (Icoaraci)",
 *   "city": "Belém",
 *   "state": "PA",
 *   "zipCode": "66815-005"
 * }
 */
data class CepResponse(
    @SerializedName("zipCode")
    val zipCode: String?,

    @SerializedName("address")
    val address: String?,

    @SerializedName("neighborhood")
    val neighborhood: String?,

    @SerializedName("city")
    val city: String?,

    @SerializedName("state")
    val state: String?
)

