package com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.events

/**
 * Eventos do BottomSheet de autenticação inline
 * Segue o padrão do SignInEvent
 */
sealed class AuthBottomSheetEvent {
    data object OnLoginClick : AuthBottomSheetEvent()
    data object OnDismiss : AuthBottomSheetEvent()
    data object OnTogglePasswordVisibility : AuthBottomSheetEvent()
}
