package com.br.xbizitwork.domain.model.auth

data class ChangePasswordModel(
    val currentPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
