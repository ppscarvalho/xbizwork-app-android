package com.br.xbizitwork.ui.presentation.features.auth.signup.events

sealed class SignUpEvent {
    data object OnSignUpClick : SignUpEvent()
}