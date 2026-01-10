package com.br.xbizitwork.ui.presentation.features.profile.events

import android.net.Uri
import java.time.LocalDate

/**
 * Eventos que a UI dispara quando o usuário interage
 * ATUALIZADO com todos os campos do perfil
 */
sealed class EditProfileEvent {
    // Dados básicos
    data class OnNameChanged(val name: String) : EditProfileEvent()
    data class OnCpfChanged(val cpf: String) : EditProfileEvent()
    data class OnDateOfBirthChanged(val date: LocalDate?) : EditProfileEvent()
    //data class OnGenderChanged(val gender: String) : EditProfileEvent()

    // Contato
    data class OnEmailChanged(val email: String) : EditProfileEvent()
    data class OnPhoneChanged(val phone: String) : EditProfileEvent()

    // Endereço
    data class OnZipCodeChanged(val zipCode: String) : EditProfileEvent()
    object OnZipCodeBlur : EditProfileEvent()  // ✅ NOVO: Busca endereço quando sai do campo CEP
    data class OnAddressChanged(val address: String) : EditProfileEvent()
    data class OnNumberChanged(val number: String) : EditProfileEvent()
    data class OnNeighborhoodChanged(val neighborhood: String) : EditProfileEvent()
    data class OnCityChanged(val city: String) : EditProfileEvent()
    data class OnStateChanged(val state: String) : EditProfileEvent()

    // Foto de perfil
    data class OnPhotoSelected(val uri: Uri) : EditProfileEvent()
    object OnPhotoRemove : EditProfileEvent()
    
    // Ações
    object OnSaveClick : EditProfileEvent()
    object OnCancelClick : EditProfileEvent()
    object OnConfirmCancel : EditProfileEvent()
    object OnDismissConfirmDialog : EditProfileEvent()
    
    // Ciclo de vida
    object OnScreenLoad : EditProfileEvent()
}
