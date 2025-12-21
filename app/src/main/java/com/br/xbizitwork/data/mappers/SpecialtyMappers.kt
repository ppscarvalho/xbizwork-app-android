package com.br.xbizitwork.data.mappers

import com.br.xbizitwork.data.remote.specialty.dtos.response.SpecialtyResponse
import com.br.xbizitwork.domain.result.specialty.SpecialtyResult

/**
 * Mappers para conversão entre DTOs e Domain Models de Specialty
 */

fun SpecialtyResponse.toDomain(): SpecialtyResult {
    return SpecialtyResult(
        id = id,
        description = description,
        categoryId = categoryId
    )
}

fun List<SpecialtyResponse>.toDomain(): List<SpecialtyResult> {
    return map { it.toDomain() }
}
