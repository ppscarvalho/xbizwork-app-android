package com.br.xbizitwork.ui.presentation.features.professionalprofile.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.features.professionalprofile.events.ProfessionalProfileEvent
import com.br.xbizitwork.ui.presentation.features.professionalprofile.state.ProfessionalProfileUiState
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<ProfessionalProfileUiState> =
        MutableStateFlow(ProfessionalProfileUiState())
    val uiState: StateFlow<ProfessionalProfileUiState> = _uiState.asStateFlow()

    init {
        // Get professional ID from navigation arguments
        val professionalId = savedStateHandle.toRoute<MenuScreens.ProfessionalProfileScreen>().professionalId
        logInfo("PROFESSIONAL_PROFILE_VM", "Professional ID received: $professionalId")
        
        // TODO: In a real scenario, we would need to:
        // 1. Either pass the full professional object via navigation
        // 2. Or store the professionals list in a shared state/repository
        // For now, we'll handle this in the navigation setup
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
        if (professional != null) {
            logInfo("PROFESSIONAL_PROFILE_VM", "===========================================")
            logInfo("PROFESSIONAL_PROFILE_VM", "📞 INICIANDO CONTATO COM PROFISSIONAL")
            logInfo("PROFESSIONAL_PROFILE_VM", "===========================================")
            logInfo("PROFESSIONAL_PROFILE_VM", "👤 Nome: ${professional.name}")
            logInfo("PROFESSIONAL_PROFILE_VM", "📱 Telefone: ${professional.mobilePhone}")
            logInfo("PROFESSIONAL_PROFILE_VM", "===========================================")
            // TODO: Implement contact flow (WhatsApp, phone call, etc)
            // This will be defined by the project's contact mechanism
        }
    }
}
