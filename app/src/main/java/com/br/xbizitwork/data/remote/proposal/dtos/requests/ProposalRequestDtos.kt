package com.br.xbizitwork.data.remote.proposal.dtos.requests

import com.google.gson.annotations.SerializedName

/**
 * DTO: Request para criar uma nova proposta
 */
data class CreateProposalRequestDto(
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
    
    @SerializedName("latitude")
    val latitude: Double,
    
    @SerializedName("longitude")
    val longitude: Double,
    
    @SerializedName("address_details")
    val addressDetails: String? = null,
    
    @SerializedName("preferred_date")
    val preferredDate: String? = null, // "YYYY-MM-DD"
    
    @SerializedName("preferred_time_start")
    val preferredTimeStart: String? = null, // "HH:mm"
    
    @SerializedName("preferred_time_end")
    val preferredTimeEnd: String? = null,
    
    @SerializedName("estimated_budget_min")
    val estimatedBudgetMin: Double?,
    
    @SerializedName("estimated_budget_max")
    val estimatedBudgetMax: Double?,
)

/**
 * DTO: Request para responder uma proposta
 */
data class RespondProposalRequestDto(
    @SerializedName("proposal_id")
    val proposalId: String,
    
    @SerializedName("status")
    val status: String, // "ACCEPTED", "DECLINED", "COUNTER_OFFER"
    
    @SerializedName("message")
    val message: String? = null,
    
    @SerializedName("suggested_price")
    val suggestedPrice: Double? = null,
    
    @SerializedName("estimated_date")
    val estimatedDate: String? = null, // "YYYY-MM-DD"
)

/**
 * DTO: Request para deixar uma avaliação
 */
data class SubmitReviewRequestDto(
    @SerializedName("proposal_id")
    val proposalId: String,
    
    @SerializedName("rating")
    val rating: Float, // 1-5
    
    @SerializedName("comment")
    val comment: String,
    
    @SerializedName("cleanliness")
    val cleanliness: Float? = null,
    
    @SerializedName("punctuality")
    val punctuality: Float? = null,
    
    @SerializedName("communication")
    val communication: Float? = null,
    
    @SerializedName("work_quality")
    val workQuality: Float? = null,
    
    @SerializedName("is_recommended")
    val isRecommended: Boolean = true,
)
