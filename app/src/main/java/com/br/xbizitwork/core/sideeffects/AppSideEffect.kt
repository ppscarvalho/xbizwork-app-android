package com.br.xbizitwork.core.sideeffects

sealed interface AppSideEffect {
    data class ShowToast(val message: String) : AppSideEffect
    data object NavigateToHomeGraph : AppSideEffect
    data object NavigateBack : AppSideEffect
    data class OpenExternalUrl(val url: String) : AppSideEffect
}

