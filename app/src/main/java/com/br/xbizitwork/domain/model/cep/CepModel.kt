package com.br.xbizitwork.domain.model.cep

/**
 * Modelo de domínio para dados de CEP
 */
data class CepModel(
    val cep: String,
    val logradouro: String,
    val bairro: String,
    val cidade: String,
    val estado: String
)

