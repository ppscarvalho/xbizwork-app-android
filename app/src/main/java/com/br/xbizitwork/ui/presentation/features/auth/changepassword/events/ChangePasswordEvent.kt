package com.br.xbizitwork.ui.presentation.features.auth.changepassword.events

sealed class ChangePasswordEvent {
    object OnChangePasswordClick : ChangePasswordEvent()
}
