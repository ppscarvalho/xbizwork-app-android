package com.br.xbizitwork.ui.presentation.features.auth.changepassword.events

/**
 * Eventos da tela de alteração de senha
 * Seguindo o mesmo padrão do SignUpEvent
 */
sealed class ChangePasswordEvent {
    data object OnChangePasswordClick : ChangePasswordEvent()
}
