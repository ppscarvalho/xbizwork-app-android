package com.br.xbizitwork.domain.model.professional

import java.time.LocalDateTime

/**
 * Domínio: Perfil profissional
 *
 * Representa um profissional cadastrado no marketplace.
 * Contém informações públicas visíveis para clientes e métricas de desempenho.
 */
data class ProfessionalProfile(
    val id: String,
    val userId: String,
    val name: String,
    val email: String,
    val phone: String,
    val profilePhotoUrl: String? = null,
    
    // Especialidades
    val serviceCategories: List<ServiceCategory>,
    val bio: String? = null,
    val yearsOfExperience: Int,
    
    // Localização
    val city: String,
    val state: String,
    val latitude: Double,
    val longitude: Double,
    
    // Métricas
    val averageRating: Float = 0f, // 0-5 stars
    val totalReviews: Int = 0,
    val totalCompletedServices: Int = 0,
    val responseTime: Int = 0, // minutos
    val acceptanceRate: Float = 100f, // percentual
    
    // Disponibilidade
    val isAvailable: Boolean = true,
    val isVerified: Boolean = false,
    
    // Preços
    val pricePerHourMin: Double? = null,
    val pricePerHourMax: Double? = null,
    
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

/**
 * Categorias de serviço suportadas
 */
enum class ServiceCategory {
    CLEANING,          // Limpeza
    HOME_REPAIR,       // Reparo em casa
    PLUMBING,          // Encanador
    ELECTRICAL,        // Eletricista
    PAINTING,          // Pintura
    PERSONAL_TRAINER,  // Personal trainer
    TUTOR,             // Professor/reforço
    MANICURE,          // Manicure/pedicure
    HAIRDRESSER,       // Cabelereiro
    MECHANIC,          // Mecânico
    GARDENING,         // Jardinagem
    PHOTOGRAPHY,       // Fotografia
    VIDEO_PRODUCTION,  // Produção de vídeo
    OTHER              // Outro
}

/**
 * Status de verificação do profissional
 */
enum class VerificationStatus {
    PENDING,           // Aguardando análise
    VERIFIED,          // Verificado
    REJECTED,          // Rejeitado
    SUSPENDED          // Suspenso
}
