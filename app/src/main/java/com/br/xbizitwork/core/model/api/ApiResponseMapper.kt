package com.br.xbizitwork.core.model.api

import com.br.xbizitwork.core.result.DefaultResult

/**
 * Mappers genéricos para converter ApiResponse para DefaultResult
 */

/**
 * Converte ApiResponse<T> para DefaultResult<T>
 * Usa para Data Layer
 */
fun <T> ApiResponse<T>.toDefaultResult(): DefaultResult<T?> {
    return if (isSuccessful) {
        DefaultResult.Success(data = data)
    } else {
        DefaultResult.Error(message = message)
    }
}

/**
 * Converte ApiResponse<T> para DefaultResult<T>
 * Usa para Domain Layer
 */
fun <T> ApiResponse<T>.toDomainResult(): DefaultResult<T?> {
    return if (isSuccessful) {
        DefaultResult.Success(data = data)
    } else {
        DefaultResult.Error(message = message)
    }
}

/**
 * Converte DefaultResult<T> para ApiResponse<T>
 * Usa para criar respostas mock em testes
 */
fun <T> DefaultResult<T>.toApiResponse(message: String = ""): ApiResponse<T> {
    return when (this) {
        is DefaultResult.Success -> ApiResponse(
            data = data,
            isSuccessful = true,
            message = message.ifEmpty { "Success" }
        )
        is DefaultResult.Error -> ApiResponse(
            data = null as T,
            isSuccessful = false,
            message = this.message
        )
    }
}
