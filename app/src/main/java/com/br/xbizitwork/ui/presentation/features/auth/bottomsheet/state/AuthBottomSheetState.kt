package com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.state

/**
 * Estado do BottomSheet de autenticação inline
 * Segue o padrão do SignInState
 */
data class AuthBottomSheetState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val passwordVisible: Boolean = false,
    val errorMessage: String = "",
    val isFormValid: Boolean = false
)
