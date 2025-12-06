package com.br.xbizitwork.data.remote.proposal.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO: Response para operações com propostas
 */
data class ProposalResponseDto(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("client_id")
    val clientId: String,
    
    @SerializedName("professional_id")
    val professionalId: String?,
    
    @SerializedName("title")
    val title: String,
    
    @SerializedName("description")
    val description: String,
    
    @SerializedName("category")
    val category: String,
    
    @SerializedName("service_city")
    val serviceCity: String,
    
    @SerializedName("service_state")
    val serviceState: String,
    
    @SerializedName("status")
    val status: String,
    
    @SerializedName("estimated_budget_min")
    val estimatedBudgetMin: Double?,
    
    @SerializedName("estimated_budget_max")
    val estimatedBudgetMax: Double?,
    
    @SerializedName("final_price")
    val finalPrice: Double? = null,
    
    @SerializedName("created_at")
    val createdAt: String,
    
    @SerializedName("accepted_at")
    val acceptedAt: String? = null,
    
    @SerializedName("completed_at")
    val completedAt: String? = null,
)

/**
 * DTO: Lista paginada de propostas
 */
data class ProposalListResponseDto(
    @SerializedName("data")
    val data: List<ProposalResponseDto>,
    
    @SerializedName("pagination")
    val pagination: PaginationDto,
)

data class PaginationDto(
    @SerializedName("current_page")
    val currentPage: Int,
    
    @SerializedName("page_size")
    val pageSize: Int,
    
    @SerializedName("total")
    val total: Int,
    
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
)

/**
 * DTO: Response de avaliação
 */
data class ReviewResponseDto(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("proposal_id")
    val proposalId: String,
    
    @SerializedName("professional_id")
    val professionalId: String,
    
    @SerializedName("rating")
    val rating: Float,
    
    @SerializedName("comment")
    val comment: String,
    
    @SerializedName("created_at")
    val createdAt: String,
)
