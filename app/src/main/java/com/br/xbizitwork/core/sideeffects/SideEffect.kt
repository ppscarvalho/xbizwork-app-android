package com.br.xbizitwork.core.sideeffects

sealed interface SideEffect {
    data class ShowToast(val message: String) : SideEffect
}