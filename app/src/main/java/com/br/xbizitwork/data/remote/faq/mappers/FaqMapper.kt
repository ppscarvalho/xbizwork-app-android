package com.br.xbizitwork.data.remote.faq.mappers

import com.br.xbizitwork.data.remote.faq.dtos.responses.FaqQuestionResponse
import com.br.xbizitwork.data.remote.faq.dtos.responses.FaqSectionResponse
import com.br.xbizitwork.domain.model.faq.FaqQuestionModel
import com.br.xbizitwork.domain.model.faq.FaqSectionModel

/**
 * Mapper para converter DTOs de FAQ em modelos de domínio
 */

/**
 * Converte FaqQuestionResponse para FaqQuestionModel
 */
fun FaqQuestionResponse.toDomain(): FaqQuestionModel {
    return FaqQuestionModel(
        id = id,
        question = question,
        answer = answer,
        order = order
    )
}

/**
 * Converte FaqSectionResponse para FaqSectionModel
 */
fun FaqSectionResponse.toDomain(): FaqSectionModel {
    return FaqSectionModel(
        id = id,
        title = title,
        description = description,
        order = order,
        questions = questions.map { it.toDomain() }
    )
}
