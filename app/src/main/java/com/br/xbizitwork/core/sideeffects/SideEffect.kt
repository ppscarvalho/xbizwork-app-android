package com.br.xbizitwork.core.sideeffects

sealed interface SideEffect {
    data class ShowToast(val message: String) : SideEffect
    data object NavigateToLogin : SideEffect
    data object NavigateBack : SideEffect
}