package com.br.xbizitwork.core.model.api

import com.google.gson.annotations.SerializedName

/**
 * DTO genérico para resposta da API Node.js
 * 
 * Funciona com:
 * - Objetos simples: ApiResponse<UserDTO>
 * - Listas: ApiResponse<List<CategoryDTO>>
 * - Maps: ApiResponse<Map<String, Any>>
 * 
 * Exemplo JSON:
 * {
 *     "data": {...} ou [...],
 *     "isSuccessful": true,
 *     "message": "..."
 * }
 */
data class ApiResponse<T>(
    @SerializedName("data")
    val data: T? = null,
    
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    
    @SerializedName("message")
    val message: String
)
