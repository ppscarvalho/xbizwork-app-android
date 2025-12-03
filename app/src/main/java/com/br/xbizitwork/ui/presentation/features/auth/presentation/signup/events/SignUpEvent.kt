package com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.events

sealed class SignUpEvent {
    data object OnSignUpClick : SignUpEvent()
}