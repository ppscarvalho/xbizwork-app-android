package com.br.xbizitwork.data.mappers

import com.br.xbizitwork.data.model.category.CategoryModel
import com.br.xbizitwork.data.remote.category.dtos.response.CategoryResponse
import com.br.xbizitwork.domain.result.category.CategoryResult

/**
 * Converte CategoryResponse (DTO da API com @SerializedName)
 * para CategoryModel (modelo interno da Data Layer)
 */
fun CategoryResponse.toCategoryModel(): CategoryModel {
    return CategoryModel(
        id = this.id,
        description = this.description
    )
}

/**
 * Mapeia CategoryModel (Data Layer)
 * para CategoryResult (Domain Layer)
 */
fun CategoryModel.toDomainResult(): CategoryResult {
    return CategoryResult(
        id = this.id,
        description = this.description
    )
}
