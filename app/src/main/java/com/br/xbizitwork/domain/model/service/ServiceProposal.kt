package com.br.xbizitwork.domain.model.service

import java.time.LocalDateTime

/**
 * Domínio: Proposta de Serviço
 *
 * Representa uma proposta criada por um cliente para contratar um profissional.
 * Fluxo: PENDING → ACCEPTED → IN_PROGRESS → COMPLETED/CANCELED
 */
data class ServiceProposal(
    val id: String,
    val clientId: String,
    val professionalId: String?,
    val title: String,
    val description: String,
    val category: String, // ServiceCategory.name
    
    // Localização onde o serviço será executado
    val serviceCity: String,
    val serviceState: String,
    val latitude: Double,
    val longitude: Double,
    val addressDetails: String? = null,
    
    // Datas
    val preferredDate: LocalDateTime? = null,
    val preferredTimeStart: String? = null, // "HH:mm"
    val preferredTimeEnd: String? = null,
    
    // Orçamento
    val estimatedBudgetMin: Double?,
    val estimatedBudgetMax: Double?,
    val finalPrice: Double? = null,
    val currency: String = "BRL",
    
    // Status
    val status: ProposalStatus = ProposalStatus.PENDING,
    
    // Histórico
    val createdAt: LocalDateTime,
    val acceptedAt: LocalDateTime? = null,
    val completedAt: LocalDateTime? = null,
    val canceledAt: LocalDateTime? = null,
    val cancelReason: String? = null,
)

/**
 * Status possíveis de uma proposta
 */
enum class ProposalStatus {
    PENDING,           // Aguardando aceite
    ACCEPTED,          // Profissional aceitou
    IN_PROGRESS,       // Serviço em andamento
    COMPLETED,         // Serviço concluído
    CANCELED,          // Cancelado
    DISPUTED           // Em disputa
}

/**
 * Domínio: Avaliação e Review
 *
 * Representa um review deixado pelo cliente após completar um serviço.
 */
data class ServiceReview(
    val id: String,
    val proposalId: String,
    val professionalId: String,
    val clientId: String,
    
    val rating: Float, // 1-5 stars
    val comment: String,
    
    val cleanliness: Float? = null,
    val punctuality: Float? = null,
    val communication: Float? = null,
    val workQuality: Float? = null,
    
    val isRecommended: Boolean = true,
    val attachedPhotos: List<String> = emptyList(),
    
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null,
)

/**
 * Domínio: Resposta do Profissional à Proposta
 *
 * Quando um profissional recebe uma proposta, pode aceitar ou recusar.
 */
data class ProposalResponse(
    val id: String,
    val proposalId: String,
    val professionalId: String,
    
    val status: ResponseStatus,
    val message: String? = null,
    val suggestedPrice: Double? = null,
    val estimatedDate: String? = null, // "YYYY-MM-DD"
    
    val createdAt: LocalDateTime,
    val respondedAt: LocalDateTime? = null,
)

enum class ResponseStatus {
    PENDING,           // Cliente ainda não respondeu
    ACCEPTED,          // Profissional aceita
    DECLINED,          // Profissional recusa
    COUNTER_OFFER      // Profissional faz contraproposta
}
