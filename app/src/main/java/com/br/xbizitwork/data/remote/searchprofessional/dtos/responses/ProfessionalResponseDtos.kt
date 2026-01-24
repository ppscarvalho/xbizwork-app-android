package com.br.xbizitwork.data.remote.searchprofessional.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO: Resposta da API para busca de profissionais
 *
 * Mapeado automaticamente pelo Gson para SearchResult do domínio
 */
data class SearchProfessionalsResponseDto(
    @SerializedName("results")
    val results: List<ProfessionalSearchResultDto>,
    
    @SerializedName("total")
    val total: Int,
    
    @SerializedName("current_page")
    val currentPage: Int,
    
    @SerializedName("page_size")
    val pageSize: Int,
    
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
)

data class ProfessionalSearchResultDto(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("category")
    val category: String,
    
    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String? = null,
    
    @SerializedName("rating")
    val rating: Float,
    
    @SerializedName("review_count")
    val reviewCount: Int,
    
    @SerializedName("distance_km")
    val distanceKm: Float,
    
    @SerializedName("price_per_hour_min")
    val pricePerHourMin: Double? = null,
    
    @SerializedName("is_available")
    val isAvailable: Boolean,
    
    @SerializedName("is_verified")
    val isVerified: Boolean,
    
    @SerializedName("response_time_minutes")
    val responseTime: Int,
)

/**
 * DTO: Detalhes completos do profissional
 */
data class ProfessionalDetailResponseDto(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("user_id")
    val userId: String,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("email")
    val email: String,
    
    @SerializedName("phone")
    val phone: String,
    
    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String? = null,
    
    @SerializedName("categories")
    val serviceCategories: List<String>,
    
    @SerializedName("bio")
    val bio: String? = null,
    
    @SerializedName("years_of_experience")
    val yearsOfExperience: Int,
    
    @SerializedName("city")
    val city: String,
    
    @SerializedName("state")
    val state: String,
    
    @SerializedName("latitude")
    val latitude: Double,
    
    @SerializedName("longitude")
    val longitude: Double,
    
    @SerializedName("average_rating")
    val averageRating: Float,
    
    @SerializedName("total_reviews")
    val totalReviews: Int,
    
    @SerializedName("total_completed_services")
    val totalCompletedServices: Int,
    
    @SerializedName("response_time_minutes")
    val responseTime: Int,
    
    @SerializedName("acceptance_rate")
    val acceptanceRate: Float,
    
    @SerializedName("is_available")
    val isAvailable: Boolean,
    
    @SerializedName("is_verified")
    val isVerified: Boolean,
    
    @SerializedName("price_per_hour_min")
    val pricePerHourMin: Double? = null,
    
    @SerializedName("price_per_hour_max")
    val pricePerHourMax: Double? = null,
    
    @SerializedName("created_at")
    val createdAt: String,
    
    @SerializedName("updated_at")
    val updatedAt: String,
)
