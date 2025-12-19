package com.br.xbizitwork.ui.presentation.features.profile.state

import android.net.Uri
import java.time.LocalDate

/**
 * Estado da tela de edição de perfil
 * ATUALIZADO com TODOS os campos do schema Prisma
 */
data class EditProfileUIState(
    // Dados básicos
    val userId: Int = 0,
    val name: String = "",
    val cpf: String = "",
    val dateOfBirth: LocalDate? = null,
    val gender: String = "",

    // Contato
    val email: String = "",
    val phoneNumber: String = "",

    // Endereço
    val zipCode: String = "",
    val address: String = "",
    val number: String = "",
    val neighborhood: String = "",
    val city: String = "",
    val state: String = "",

    // Localização
    val latitude: Double? = null,
    val longitude: Double? = null,

    // Foto
    val profilePhotoUri: Uri? = null,
    
    // Validações
    val nameError: String? = null,
    val emailError: String? = null,
    val phoneError: String? = null,
    val cpfError: String? = null,

    // Estados de UI
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isFormValid: Boolean = false,
    val hasChanges: Boolean = false,
    val showConfirmDialog: Boolean = false,

    // Mensagens
    val errorMessage: String? = null,
    val successMessage: String? = null
)
