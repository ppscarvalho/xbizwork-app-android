package com.br.xbizitwork.ui.presentation.features.home.state

import androidx.annotation.DrawableRes

data class HomeUIState(
    val isEmpty: Boolean = false,
    val isLoading: Boolean = false,
    val userName: String? = null,
    val logoutMessage: String? = null,  // ⚠️ REMOVIDO: Usar SideEffect via Channel em vez disso
)


