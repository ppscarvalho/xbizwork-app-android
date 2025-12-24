package com.br.xbizitwork.ui.presentation.features.profile.changepassword.events

sealed class ChangePasswordEvent {
    data object OnChangePasswordClick : ChangePasswordEvent()
}
