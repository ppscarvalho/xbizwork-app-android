package com.br.xbizitwork.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCase
import com.br.xbizitwork.ui.presentation.navigation.screens.Graphs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAuthSessionUseCase: GetAuthSessionUseCase
): ViewModel() {
    private val _isSplashLoading = MutableStateFlow(true)
    val isSlashLoading = _isSplashLoading.asStateFlow()

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getAuthSessionUseCase.invoke().collect { userSession ->
                if(userSession.token.isNotEmpty()){
                    _uiState.update {
                        it.copy(
                            startDestination = Graphs.HomeGraphs
                        )
                    }
                }else if (!userSession.errorMessage.isNullOrEmpty()){
                    logInfo("USERR_SESSEION", "UserSession ${userSession.errorMessage}")
                }
                delay(1000)
                _isSplashLoading.update { false }
            }
        }
    }

    fun setSelectedProfessional(professional: ProfessionalSearchBySkill) {
        _uiState.update {
            it.copy(selectedProfessional = professional)
        }
    }

    fun getSelectedProfessional(professionalId: Int): ProfessionalSearchBySkill? {
        return _uiState.value.selectedProfessional?.takeIf { it.id == professionalId }
    }
}