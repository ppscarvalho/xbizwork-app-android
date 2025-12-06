package com.br.xbizitwork.domain.model.service

/**
 * Domínio: Filtros de Busca
 *
 * Representa os parâmetros de busca para encontrar profissionais.
 * Usados em SearchProfessionalsUseCase.
 */
data class SearchFilters(
    // Localização
    val latitude: Double,
    val longitude: Double,
    val radiusKm: Int = 15, // raio de busca em km
    
    // Categoria
    val categories: List<String> = emptyList(), // ServiceCategory.name
    
    // Preço
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    
    // Avaliação
    val minRating: Float = 0f, // 0-5 stars
    
    // Disponibilidade
    val onlyAvailable: Boolean = true,
    val onlyVerified: Boolean = false,
    
    // Paginação
    val page: Int = 0,
    val pageSize: Int = 20,
    
    // Ordenação
    val sortBy: SortOption = SortOption.RATING,
)

enum class SortOption {
    RATING,            // Melhor avaliação
    DISTANCE,          // Mais próximo
    PRICE_LOW,         // Menor preço
    PRICE_HIGH,        // Maior preço
    NEWEST,            // Mais recente
    MOST_REVIEWS       // Mais avaliações
}

/**
 * Resultado de busca com informações resumidas
 * (otimizado para lista)
 */
data class ProfessionalSearchResult(
    val id: String,
    val name: String,
    val category: String,
    val profilePhotoUrl: String? = null,
    val rating: Float,
    val reviewCount: Int,
    val distanceKm: Float,
    val pricePerHourMin: Double?,
    val isAvailable: Boolean,
    val isVerified: Boolean,
    val responseTime: Int, // minutos
)

/**
 * Resultado paginado de busca
 */
data class SearchResult(
    val results: List<ProfessionalSearchResult>,
    val total: Int,
    val currentPage: Int,
    val pageSize: Int,
    val hasNextPage: Boolean,
)
