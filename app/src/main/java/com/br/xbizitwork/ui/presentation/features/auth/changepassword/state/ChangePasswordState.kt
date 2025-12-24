package com.br.xbizitwork.ui.presentation.features.auth.changepassword.state

import com.br.xbizitwork.domain.session.AuthSession

/**
 * Estado da tela de alteração de senha
 * Seguindo o mesmo padrão do SignUpState
 */
data class ChangePasswordState(
    val currentPassword: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",

    val isFormValid: Boolean = false,

    val fieldErrorMessage: String? = null,
    val isLoading: Boolean = false,

    val isSuccess: Boolean = false,
    val changePasswordErrorMessage: String? = null,
    val results: AuthSession? = null,
 )
