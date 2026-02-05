package com.br.xbizitwork.ui.presentation.features.professionalprofile.viewmodel

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCase
import com.br.xbizitwork.ui.presentation.features.professionalprofile.events.ProfessionalProfileEvent
import com.br.xbizitwork.ui.presentation.features.professionalprofile.state.ProfessionalProfileUiState
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for professional profile screen
 * Following the same pattern as SearchProfessionalsViewModel
 * 
 * Receives professional data through navigation arguments (professional ID)
 * In this initial version, we'll need to receive the full professional object
 * from the search results since no API call should be made
 */
@HiltViewModel
class ProfessionalProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAuthSessionUseCase: GetAuthSessionUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ProfessionalProfileUiState> =
        MutableStateFlow(ProfessionalProfileUiState())
    val uiState: StateFlow<ProfessionalProfileUiState> = _uiState.asStateFlow()

    private val _sideEffectChannel = Channel<AppSideEffect>(capacity = Channel.BUFFERED)
    val sideEffectChannel = _sideEffectChannel.receiveAsFlow()

    private val _userName = MutableStateFlow("")

    init {
        // Get professional ID from navigation arguments
        val professionalId = savedStateHandle.toRoute<MenuScreens.ProfessionalProfileScreen>().professionalId
        logInfo("PROFESSIONAL_PROFILE_VM", "Professional ID received: $professionalId")
        
        // Observar AuthSession para obter nome do usuário
        observeAuthSession()

        // TODO: In a real scenario, we would need to:
        // 1. Either pass the full professional object via navigation
        // 2. Or store the professionals list in a shared state/repository
        // For now, we'll handle this in the navigation setup
    }

    private fun observeAuthSession() {
        viewModelScope.launch {
            getAuthSessionUseCase.invoke().collect { authSession ->
                _userName.value = authSession.name
                logInfo("PROFESSIONAL_PROFILE_VM", "👤 Nome do usuário logado: ${authSession.name}")
            }
        }
    }

    fun onEvent(event: ProfessionalProfileEvent) {
        when (event) {
            is ProfessionalProfileEvent.OnContactClick -> {
                handleContactClick()
            }
        }
    }

    fun setProfessional(professional: ProfessionalSearchBySkill) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                professional = professional,
                isLoading = false,
                errorMessage = null
            )
            logInfo("PROFESSIONAL_PROFILE_VM", "Professional data set: ${professional.name}")
        }
    }

    private fun handleContactClick() {
        val professional = _uiState.value.professional
        val userName = _userName.value.ifEmpty { "Usuário" }

        if (professional != null) {
            viewModelScope.launch {
                logInfo("PROFESSIONAL_PROFILE_VM", "===========================================")
                logInfo("PROFESSIONAL_PROFILE_VM", "📱 Abrindo WhatsApp...")
                logInfo("PROFESSIONAL_PROFILE_VM", "===========================================")
                logInfo("PROFESSIONAL_PROFILE_VM", "👤 Profissional: ${professional.name}")
                logInfo("PROFESSIONAL_PROFILE_VM", "📞 Telefone: ${professional.mobilePhone}")
                logInfo("PROFESSIONAL_PROFILE_VM", "👤 Usuário: $userName")
                logInfo("PROFESSIONAL_PROFILE_VM", "===========================================")

                val message = "Olá, me chamo $userName. " +
                              "Encontrei seu perfil no aplicativo e gostaria de " +
                              "conversar sobre um trabalho de ${professional.skill.name.lowercase()}."

                val phone = professional.mobilePhone.replace(Regex("[^0-9]"), "")
                val whatsappUrl = "https://wa.me/55$phone?text=${Uri.encode(message)}"

                logInfo("PROFESSIONAL_PROFILE_VM", "🔗 URL: $whatsappUrl")

                _sideEffectChannel.send(
                    AppSideEffect.OpenExternalUrl(whatsappUrl)
                )
            }
        }
    }
}
