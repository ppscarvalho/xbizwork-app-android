package com.br.xbizitwork.data.remote.faq.mappers

import com.br.xbizitwork.data.remote.faq.dtos.responses.FaqQuestionDto
import com.br.xbizitwork.data.remote.faq.dtos.responses.FaqSectionDto
import com.br.xbizitwork.domain.model.faq.FaqQuestion
import com.br.xbizitwork.domain.model.faq.FaqSection

/**
 * Mapper extension functions to convert DTOs to domain models
 * Following the same pattern as other mappers in the project
 */

fun FaqQuestionDto.toDomain(): FaqQuestion {
    return FaqQuestion(
        id = this.id,
        question = this.question,
        answer = this.answer,
        order = this.order
    )
}

fun FaqSectionDto.toDomain(): FaqSection {
    return FaqSection(
        id = this.id,
        title = this.title,
        description = this.description,
        order = this.order,
        questions = this.questions.map { it.toDomain() }
    )
}
