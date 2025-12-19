package com.br.xbizitwork.data.mappers

import com.br.xbizitwork.data.remote.subcategory.dtos.response.SubcategoryResponse
import com.br.xbizitwork.data.remote.subcategory.dtos.response.SubcategoryResponseModel
import com.br.xbizitwork.domain.result.subcategory.SubcategoryResult

/**
 * Mapeia SubcategoryResponse (DTO da API com @SerializedName)
 * para SubcategoryResponseModel (modelo interno da Data Layer)
 */
fun SubcategoryResponse.toSubcategoryResponseModel(): SubcategoryResponseModel {
    return SubcategoryResponseModel(
        id = this.id,
        description = this.description,
        categoryId = this.categoryId
    )
}

/**
 * Mapeia SubcategoryResponseModel (Data Layer)
 * para SubcategoryResult (Domain Layer)
 */
fun SubcategoryResponseModel.toDomainResult(): SubcategoryResult {
    return SubcategoryResult(
        id = this.id,
        description = this.description,
        categoryId = this.categoryId
    )
}
