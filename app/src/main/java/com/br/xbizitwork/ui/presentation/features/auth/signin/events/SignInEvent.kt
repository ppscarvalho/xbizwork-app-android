package com.br.xbizitwork.ui.presentation.features.auth.signin.events

sealed class SignInEvent {
    data object OnSignInClick : SignInEvent()
}