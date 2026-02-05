package com.br.xbizitwork.domain.model.plan

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector

data class PlanBenefit(
    val icon: ImageVector,
    val text: String
)

/**
 * Faz o parsing da descrição do plano que vem no formato:
 * "icon_name:Texto do benefício|icon_name:Texto do benefício"
 *
 * Exemplo: "schedule:30 dias|check_circle:Acesso inicial|rocket_launch:Ideal para testes"
 */
fun parsePlanDescription(description: String): List<PlanBenefit> {
    return description.split("|").mapNotNull { benefit ->
        val parts = benefit.split(":", limit = 2)
        if (parts.size == 2) {
            val iconName = parts[0].trim()
            val text = parts[1].trim()
            val icon = getIconFromName(iconName)
            PlanBenefit(icon, text)
        } else null
    }
}

/**
 * Mapeia o nome do ícone (vindo da API) para o ícone Material correspondente
 */
private fun getIconFromName(name: String): ImageVector {
    return when (name) {
        "schedule" -> Icons.Default.Schedule
        "check_circle" -> Icons.Default.CheckCircle
        "rocket_launch" -> Icons.Default.RocketLaunch
        "photo" -> Icons.Default.Photo
        "person" -> Icons.Default.Person
        "collections" -> Icons.Default.Collections
        "star" -> Icons.Default.Star
        "trending_up" -> Icons.Default.TrendingUp
        else -> Icons.Default.Circle // Fallback para ícones desconhecidos
    }
}
