package com.br.xbizitwork.ui.presentation.features.searchprofessionals.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.util.DistanceCalculator
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.state.ProfessionalMapUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para tela de mapa com profissional em destaque
 * Filtra profissionais próximos usando raio de distância
 */
@HiltViewModel
class ProfessionalMapViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfessionalMapUiState())
    val uiState: StateFlow<ProfessionalMapUiState> = _uiState.asStateFlow()


    /**
     * Inicializa o mapa com o profissional selecionado e filtra os próximos
     */
    fun initializeMap(
        selectedProfessional: ProfessionalSearchBySkill,
        allProfessionals: List<ProfessionalSearchBySkill>
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val lat = selectedProfessional.latitude
            val lon = selectedProfessional.longitude

            if (lat != null && lon != null) {
                // Filtrar profissionais próximos (excluindo o selecionado)
                val nearby = DistanceCalculator.filterByRadius(
                    centerLat = lat,
                    centerLon = lon,
                    professionals = allProfessionals.filter { it.id != selectedProfessional.id },
                    radiusKm = _uiState.value.radiusKm
                )

                logInfo("PROFESSIONAL_MAP_VM", "Profissionais próximos encontrados: ${nearby.size}")

                _uiState.update {
                    it.copy(
                        selectedProfessional = selectedProfessional,
                        allProfessionals = allProfessionals,
                        nearbyProfessionals = nearby,
                        isLoading = false,
                        errorMessage = null
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Profissional não possui localização definida"
                    )
                }
            }
        }
    }

    /**
     * Atualiza o raio de busca e recalcula profissionais próximos
     */
    fun updateRadius(newRadiusKm: Double) {
        val selected = _uiState.value.selectedProfessional ?: return
        val lat = selected.latitude ?: return
        val lon = selected.longitude ?: return

        viewModelScope.launch {
            val nearby = DistanceCalculator.filterByRadius(
                centerLat = lat,
                centerLon = lon,
                professionals = _uiState.value.allProfessionals.filter { it.id != selected.id },
                radiusKm = newRadiusKm
            )

            _uiState.update {
                it.copy(
                    radiusKm = newRadiusKm,
                    nearbyProfessionals = nearby
                )
            }

            logInfo("PROFESSIONAL_MAP_VM", "Raio atualizado para ${newRadiusKm}km - ${nearby.size} próximos")
        }
    }
}
