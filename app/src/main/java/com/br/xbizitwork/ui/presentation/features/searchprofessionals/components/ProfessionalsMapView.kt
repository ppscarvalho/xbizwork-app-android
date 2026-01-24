package com.br.xbizitwork.ui.presentation.features.searchprofessionals.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

/**
 * Componente do mapa que exibe os profissionais encontrados
 * Ocupa toda a área disponível da tela
 */
@Composable
fun ProfessionalsMapView(
    professionals: List<ProfessionalSearchBySkill>,
    modifier: Modifier = Modifier,
    onProfessionalClick: (ProfessionalSearchBySkill) -> Unit = {}
) {
    // Filtrar profissionais que têm coordenadas válidas
    val professionalsWithLocation = remember(professionals) {
        professionals.filter {
            it.latitude != null && it.longitude != null
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        if (professionalsWithLocation.isEmpty()) {
            // Mensagem quando não há profissionais com localização
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum profissional com localização disponível",
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )
            }
        } else {
            // Calcular o centro do mapa baseado nos profissionais
            val centerPosition = remember(professionalsWithLocation) {
                val firstProfessional = professionalsWithLocation.first()
                LatLng(
                    firstProfessional.latitude ?: 0.0,
                    firstProfessional.longitude ?: 0.0
                )
            }

            // Estado da câmera
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(centerPosition, 12f)
            }

            // Ajustar câmera para mostrar todos os markers
            LaunchedEffect(professionalsWithLocation) {
                if (professionalsWithLocation.size > 1) {
                    val boundsBuilder = LatLngBounds.builder()
                    professionalsWithLocation.forEach { professional ->
                        professional.latitude?.let { lat ->
                            professional.longitude?.let { lng ->
                                boundsBuilder.include(LatLng(lat, lng))
                            }
                        }
                    }
                    val bounds = boundsBuilder.build()
                    val padding = 100 // Padding em pixels
                    cameraPositionState.move(
                        CameraUpdateFactory.newLatLngBounds(bounds, padding)
                    )
                }
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(
                    isMyLocationEnabled = false // Desabilitado - requer permissão
                ),
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = true,
                    myLocationButtonEnabled = false,
                    compassEnabled = true,
                    mapToolbarEnabled = true
                )
            ) {
                // Adicionar marker para cada profissional
                professionalsWithLocation.forEach { professional ->
                    professional.latitude?.let { lat ->
                        professional.longitude?.let { lng ->
                            Marker(
                                state = MarkerState(position = LatLng(lat, lng)),
                                title = professional.name,
                                snippet = "${professional.skill.description}\n${professional.mobilePhone}\n${professional.city} - ${professional.state}",
                                onInfoWindowClick = {
                                    // Quando a InfoWindow for clicada (segundo toque), chamar o callback
                                    onProfessionalClick(professional)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    backgroundColor = 0xFF0f344e
)
@Composable
private fun ProfessionalsMapViewPreview() {
    XBizWorkTheme {
        ProfessionalsMapView(
            professionals = listOf(
                ProfessionalSearchBySkill(
                    id = 14,
                    name = "Paula Manuela",
                    mobilePhone = "(91) 99999-9999",
                    city = "Belém",
                    state = "PA",
                    latitude = -1.4566499,
                    longitude = -48.4827653,
                    skill = SkillInfo(9, "Educador Físico")
                ),
                ProfessionalSearchBySkill(
                    id = 13,
                    name = "Pedro Carvalho",
                    mobilePhone = "(91) 99999-9999",
                    city = "Belém",
                    state = "PA",
                    latitude = -1.3158193,
                    longitude = -48.4540928,
                    skill = SkillInfo(9, "Educador Físico")
                )
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}

