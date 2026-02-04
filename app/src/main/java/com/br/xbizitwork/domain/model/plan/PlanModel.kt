package com.br.xbizitwork.domain.model.plan

data class PlanModel(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val durationInDays: Int,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String
) {
    /**
     * Retorna os benefícios do plano parseados com seus respectivos ícones
     */
    fun getBenefits(): List<PlanBenefit> {
        return parsePlanDescription(description)
    }
}
