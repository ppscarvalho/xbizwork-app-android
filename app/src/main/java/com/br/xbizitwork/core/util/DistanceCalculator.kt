package com.br.xbizitwork.core.util

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Utilidade para cálculo de distância entre coordenadas geográficas
 * Usa a fórmula de Haversine
 */
object DistanceCalculator {

    private const val EARTH_RADIUS_KM = 6371.0

    /**
     * Calcula a distância entre duas coordenadas geográficas
     * @param lat1 Latitude do ponto 1
     * @param lon1 Longitude do ponto 1
     * @param lat2 Latitude do ponto 2
     * @param lon2 Longitude do ponto 2
     * @return Distância em quilômetros
     */
    fun calculateDistance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return EARTH_RADIUS_KM * c
    }

    /**
     * Filtra profissionais dentro de um raio específico
     */
    fun filterByRadius(
        centerLat: Double,
        centerLon: Double,
        professionals: List<com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill>,
        radiusKm: Double = 10.0
    ): List<com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill> {
        return professionals.filter { professional ->
            val lat = professional.latitude
            val lon = professional.longitude

            if (lat != null && lon != null) {
                val distance = calculateDistance(centerLat, centerLon, lat, lon)
                distance <= radiusKm
            } else {
                false
            }
        }
    }
}
