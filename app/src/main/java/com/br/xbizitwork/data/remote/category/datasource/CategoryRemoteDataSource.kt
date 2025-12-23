package com.br.xbizitwork.data.remote.category.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.model.category.CategoryModel

/**
 * Interface para acesso remoto aos dados de categorias
 * 
 * Retorna DefaultResult para permitir tratamento unificado de erros
 * na camada de dados, antes de converter para DefaultResult no Repository
 */
interface CategoryRemoteDataSource {
    suspend fun getAllCategory(): DefaultResult<List<CategoryModel>>
}