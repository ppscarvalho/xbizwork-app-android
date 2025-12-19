package com.br.xbizitwork.data.remote.user.dtos.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response WRAPPER da API GET /user/{id}
 * A API retorna um objeto com estrutura:
 * {
 *   "data": { ... dados do usuário ... },
 *   "isSuccessful": true,
 *   "message": "Usuário encontrado com sucesso!"
 * }
 *
 * NOTA: data pode ser null se o usuário não for encontrado!
 */
@Serializable
data class UserApiResponse(
    @SerialName("data") val data: UserResponse?,  // ✅ NULLABLE!
    @SerialName("isSuccessful") val isSuccessful: Boolean,
    @SerialName("message") val message: String
)

