package com.br.xbizitwork.ui.presentation.features.auth.presentation.signin.events

sealed class SignInEvent {
    data object OnSignInClick : SignInEvent()
}