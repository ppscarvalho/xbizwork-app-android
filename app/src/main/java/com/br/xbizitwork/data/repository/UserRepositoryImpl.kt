package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.user.datasource.UserRemoteDataSource
import com.br.xbizitwork.data.remote.user.mappers.toModel
import com.br.xbizitwork.domain.model.user.UserModel
import com.br.xbizitwork.domain.repository.UserRepository
import kotlinx.coroutines.withContext

class UserRepositoryImpl constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
): UserRepository {
    override suspend fun getUserById(userId: Int): DefaultResult<UserModel> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.getUserById(userId)

            // ✅ Log da resposta da API
            logInfo(
                "GET_USER_BY_ID",
                "API Response: isSuccessful=${response.isSuccessful}, message=${response.message}"
            )

            // ⚠️ VALIDAÇÃO: data pode ser null!
            if (response.data == null) {
                logInfo(
                    "GET_USER_BY_ID",
                    "ERRO: apiResponse.data é NULL! isSuccessful=${response.isSuccessful}, message=${response.message}"
                )
                throw IllegalStateException("Dados do usuário não encontrados: ${response.message}")
            }

            logInfo(
                "GET_USER_BY_ID",
                "Dados recebidos: name=${response.data.name}, cpf=${response.data.cpf}"
            )

            when {
                response.isSuccessful -> {
                    DefaultResult.Success(response.data.toModel())
                }
                else -> {
                    DefaultResult.Error(null, response.message)
                }
            }
        }
}
