package com.br.xbizitwork.ui.presentation.features.searchprofessionals.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.components.bottomsheet.ProfessionalQuickViewBottomSheet
import com.br.xbizitwork.ui.presentation.components.state.ErrorState
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.state.ProfessionalMapUiState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * Tela de mapa com profissional em destaque
 * Estilo Booking.com - destaca o profissional selecionado e mostra próximos
 *
 * Implementa BottomSheet para visualização rápida de profissionais não selecionados
 */
@Composable
fun ProfessionalMapScreen(
    uiState: ProfessionalMapUiState,
    selectedProfessional: ProfessionalSearchBySkill?,
    allProfessionals: List<ProfessionalSearchBySkill>,
    onNavigateBack: () -> Unit,
    onInitializeMap: (ProfessionalSearchBySkill, List<ProfessionalSearchBySkill>) -> Unit,
    onProfessionalClick: (ProfessionalSearchBySkill) -> Unit = {},
    setSelectedProfessional: (ProfessionalSearchBySkill) -> Unit
) {
    // Estado para controlar o profissional a ser exibido no BottomSheet
    var selectedForQuickView by remember { mutableStateOf<ProfessionalSearchBySkill?>(null) }

    // Inicializar mapa quando a tela carrega
    LaunchedEffect(selectedProfessional) {
        if (selectedProfessional != null && uiState.selectedProfessional == null) {
            onInitializeMap(selectedProfessional, allProfessionals)
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Profissionais Próximos",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                when {
                    uiState.isLoading || uiState.isLoadingMap -> {
                        LoadingIndicator(message = "Carregando mapa e profissionais próximos...")
                    }

                    uiState.errorMessage != null -> {
                        ErrorState(
                            message = uiState.errorMessage,
                            onRetry = {
                                if (selectedProfessional != null) {
                                    onInitializeMap(selectedProfessional, allProfessionals)
                                }
                            }
                        )
                    }

                    uiState.selectedProfessional != null -> {
                        ProfessionalMapWithHighlight(
                            selectedProfessional = uiState.selectedProfessional,
                            nearbyProfessionals = uiState.nearbyProfessionals,
                            onSelectedProfessionalClick = { professional ->
                                // Marcador VERMELHO - navega direto para o perfil
                                onProfessionalClick(professional)
                            },
                            onNearbyProfessionalClick = { professional ->
                                // Marcador AZUL - mostra BottomSheet
                                selectedForQuickView = professional
                            }
                        )
                    }
                }

                // BottomSheet de visualização rápida
                ProfessionalQuickViewBottomSheet(
                    professional = selectedForQuickView,
                    isVisible = selectedForQuickView != null,
                    onDismiss = { selectedForQuickView = null },
                    onViewProfile = { professional ->
                        // Fecha o BottomSheet
                        selectedForQuickView = null
                        // Navega para o perfil do profissional clicado
                        // Não atualiza o selectedProfessional para não sobrescrever o original
                        onProfessionalClick(professional)
                    }
                )
            }
        }
    )
}

/**
 * Componente do mapa com destaque para o profissional selecionado
 *
 * Usa callbacks separados para garantir comportamentos diferentes:
 * - Marcador VERMELHO: Navega direto para o perfil
 * - Marcadores AZUIS: Mostram BottomSheet com visualização rápida
 */
@Composable
private fun ProfessionalMapWithHighlight(
    selectedProfessional: ProfessionalSearchBySkill,
    nearbyProfessionals: List<ProfessionalSearchBySkill>,
    onSelectedProfessionalClick: (ProfessionalSearchBySkill) -> Unit,
    onNearbyProfessionalClick: (ProfessionalSearchBySkill) -> Unit
) {
    val selectedLat = selectedProfessional.latitude ?: return
    val selectedLon = selectedProfessional.longitude ?: return

    val centerPosition = LatLng(selectedLat, selectedLon)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerPosition, 13f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = false),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = true,
            myLocationButtonEnabled = false,
            compassEnabled = true,
            mapToolbarEnabled = true
        )
    ) {
        // MARKER DESTACADO - Profissional selecionado (VERMELHO)
        // Navega DIRETO para o perfil ao clicar
        Marker(
            state = MarkerState(position = centerPosition),
            title = "⭐ ${selectedProfessional.name}",
            snippet = "${selectedProfessional.skill.description}\n${selectedProfessional.city} - ${selectedProfessional.state}",
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
            onInfoWindowClick = {
                onSelectedProfessionalClick(selectedProfessional)
            }
        )

        // MARKERS NORMAIS - Profissionais próximos (AZUL)
        // Mostram BottomSheet de visualização rápida ao clicar
        nearbyProfessionals.forEach { professional ->
            professional.latitude?.let { lat ->
                professional.longitude?.let { lon ->
                    Marker(
                        state = MarkerState(position = LatLng(lat, lon)),
                        title = professional.name,
                        snippet = "${professional.skill.description}\n${professional.city} - ${professional.state}",
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                        onInfoWindowClick = {
                            onNearbyProfessionalClick(professional)
                        }
                    )
                }
            }
        }
    }
}
