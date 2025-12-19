package com.br.xbizitwork.core.network

import java.io.IOException
import java.util.concurrent.TimeoutException

/**
 * Hierarquia de erros de rede com tipos específicos.
 * Facilita tratamento diferenciado por tipo de erro.
 */
sealed class NetworkError(override val message: String) : Throwable(message) {
    /**
     * Erro de conexão (sem internet, timeout, etc)
     */
    data class ConnectionError(override val message: String) : NetworkError(message)

    /**
     * Erro 4xx (cliente - validação, autenticação, etc)
     */
    data class ClientError(
        val statusCode: Int,
        override val message: String,
        val errorBody: String? = null
    ) : NetworkError(message)

    /**
     * Erro 5xx (servidor)
     */
    data class ServerError(
        val statusCode: Int,
        override val message: String,
        val errorBody: String? = null
    ) : NetworkError(message)

    /**
     * Erro de parsing/serialização
     */
    data class ParseError(override val message: String) : NetworkError(message)

    /**
     * Erro desconhecido
     */
    data class UnknownError(override val message: String) : NetworkError(message)
}

/**
 * Erros no nível de Domain (lógica de negócio)
 */
sealed class DomainError(override val message: String) : Throwable(message) {
    /**
     * Violação de regra de negócio
     */
    data class ValidationError(override val message: String) : DomainError(message)

    /**
     * Recurso não encontrado
     */
    data class NotFoundError(override val message: String) : DomainError(message)

    /**
     * Não autorizado
     */
    data class UnauthorizedError(override val message: String) : DomainError(message)

    /**
     * Proibido (autorizado mas sem permissão)
     */
    data class ForbiddenError(override val message: String) : DomainError(message)

    /**
     * Conflito de dados
     */
    data class ConflictError(override val message: String) : DomainError(message)
}

/**
 * Mapeador de exceções genéricas para NetworkError.
 *
 * Usado para converter exceções de diferentes fontes para tipos conhecidos.
 */
object ErrorMapper {
    fun mapThrowableToNetworkError(throwable: Throwable): NetworkError {
        return when (throwable) {
            is NetworkError -> throwable
            is IllegalArgumentException -> {
                if (throwable.message?.contains("Invalid") == true) {
                    NetworkError.ParseError(throwable.message ?: "Invalid response format")
                } else {
                    NetworkError.ParseError(throwable.message ?: "Invalid argument")
                }
            }
            is IOException -> {
                NetworkError.ConnectionError(
                    throwable.message ?: "Connection error"
                )
            }
            is TimeoutException -> {
                NetworkError.ConnectionError(
                    throwable.message ?: "Request timeout"
                )
            }
            else -> {
                NetworkError.UnknownError(
                    throwable.message ?: "Unknown error occurred"
                )
            }
        }
    }

    /**
     * Mapeia erro HTTP para DomainError apropriado.
     */
    fun mapHttpErrorToDomainError(statusCode: Int, message: String): DomainError {
        return when (statusCode) {
            400 -> DomainError.ValidationError(message)
            401 -> DomainError.UnauthorizedError(message)
            403 -> DomainError.ForbiddenError(message)
            404 -> DomainError.NotFoundError(message)
            409 -> DomainError.ConflictError(message)
            else -> DomainError.ValidationError(message)
        }
    }
}
