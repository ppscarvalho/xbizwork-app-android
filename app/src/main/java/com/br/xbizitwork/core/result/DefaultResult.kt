package com.br.xbizitwork.core.result

sealed class DefaultResult<out T> {
    data class Success<out T>(val data: T) : DefaultResult<T>()
    data class Error(val code: String? = null, val message: String) : DefaultResult<Nothing>()
}
