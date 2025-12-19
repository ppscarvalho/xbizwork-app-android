package com.br.xbizitwork.domain.common

sealed class DomainDefaultResult<out T> {
    data class Success<out T>(val data: T) : DomainDefaultResult<T>()
    data class Error(val code: String? = null, val message: String) : DomainDefaultResult<Nothing>()
    companion object
}