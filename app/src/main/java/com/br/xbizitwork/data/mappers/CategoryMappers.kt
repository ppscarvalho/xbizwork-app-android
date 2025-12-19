package com.br.xbizitwork.data.mappers

import com.br.xbizitwork.data.remote.category.dtos.response.CategoryResponse
import com.br.xbizitwork.data.remote.category.dtos.response.CategoryResponseModel
import com.br.xbizitwork.domain.result.category.CategoryResult

/**
 * Mapeia CategoryResponse (DTO da API com @SerializedName)
 * para CategoryResponseModel (modelo interno da Data Layer)
 */
fun CategoryResponse.toCategoryResponseModel(): CategoryResponseModel {
    return CategoryResponseModel(
        id = this.id,
        description = this.description
    )
}

/**
 * Mapeia CategoryResponseModel (Data Layer)
 * para CategoryResult (Domain Layer)
 */
fun CategoryResponseModel.toDomainResult(): CategoryResult {
    return CategoryResult(
        id = this.id,
        description = this.description
    )
}
