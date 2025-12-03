package com.br.xbizitwork.core.util.extensions

import com.br.xbizitwork.core.state.UiState
import kotlinx.coroutines.flow.Flow

/**
 * Extensões para facilitar o consumo de fluxos baseados em UiState.
 *
 * Centraliza o tratamento de estados comuns de tela:
 * - Loading
 * - Empty
 * - Success
 * - Error
 *
 *
 * Coleta um Flow de UiState e despacha ações conforme o estado emitido.
 *
 * Essa extensão elimina a repetição de blocos when nas telas e ViewModels,
 * padronizando o comportamento de carregamento, sucesso, vazio e erro.
 *
 * @param onLoading Executado quando o estado for Loading
 * @param onEmpty Executado quando o estado for Empty (opcional)
 * @param onSuccess Executado quando o estado for Success, entregando os dados
 * @param onFailure Executado quando o estado for Error, entregando a exceção
 */
suspend fun <T> Flow<UiState<T>>.collectUiState(
    onLoading: suspend () -> Unit,
    onEmpty: suspend () -> Unit = {},
    onSuccess:  suspend (data: T) -> Unit,
    onFailure: suspend (error: Throwable) -> Unit,
){
    collect {response ->
        when(response) {
            UiState.Empty -> onEmpty.invoke()
            UiState.Loading -> onLoading.invoke()
            is UiState.Success -> onSuccess.invoke(response.data)
            is UiState.Error -> onFailure.invoke(response.throwable)
        }
    }
}
