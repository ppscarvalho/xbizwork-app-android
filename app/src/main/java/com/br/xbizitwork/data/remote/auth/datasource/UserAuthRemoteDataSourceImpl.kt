package com.br.xbizitwork.data.remote.auth.datasource

import com.br.xbizitwork.core.exceptions.ErrorResponseException
import com.br.xbizitwork.core.mappers.toApplicationResultModel
import com.br.xbizitwork.core.network.ErrorMapper
import com.br.xbizitwork.core.network.RetryPolicy
import com.br.xbizitwork.core.network.SimpleCache
import com.br.xbizitwork.core.network.retryWithExponentialBackoff
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.mappers.toLoginRequest
import com.br.xbizitwork.data.mappers.toLoginResponseModel
import com.br.xbizitwork.data.mappers.toSignUpRequest
import com.br.xbizitwork.data.remote.auth.api.UserAuthApiService
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignUpResponseModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

class UserAuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: UserAuthApiService
): UserAuthRemoteDataSource {

    companion object {
        // Cache para respostas de autenticação (5 minutos de TTL)
        private val authCache = SimpleCache<String, SignInResponseModel>()

        // Política de retry: 3 tentativas, backoff exponencial
        private val retryPolicy = RetryPolicy(
            maxRetries = 3,
            initialDelayMs = 100L,
            maxDelayMs = 2000L,
            backoffMultiplier = 2f
        )
    }

    override suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<SignInResponseModel> {
        return try {
            val request = signInRequestModel.toLoginRequest()

            // Tenta com retry automático
            val response = retryWithExponentialBackoff(
                policy = retryPolicy,
                shouldRetry = { exception ->
                    // Faz retry apenas em erros de conexão/timeout
                    exception is IOException || exception is TimeoutException
                },
                operation = {
                    authApiService.signIn(request)
                }
            )

            if (response.isSuccessful) {
                val result = response.toLoginResponseModel()
                logInfo("REMOTE_DATA_SOURCE", "SignInResponse convertido para ResponseModel: name=${result.name}, email=${result.email}, token=${result.token}")
                // Armazena em cache por 5 minutos
                authCache.put("sign_in_${request.email}", result, ttlMs = 5 * 60 * 1000)
                DefaultResult.Success(result)
            } else {
                logInfo("REMOTE_DATA_SOURCE", "Resposta sem sucesso: ${response.message}")
                DefaultResult.Error(message = response.message)
            }

        } catch (e: ErrorResponseException) {
            DefaultResult.Error(code = e.error.httpCode.toString(), message = e.error.message)
        } catch (e: Exception) {
            // Mapeia exceção genérica para NetworkError
            val networkError = ErrorMapper.mapThrowableToNetworkError(e)
            DefaultResult.Error(message = networkError.message)
        }
    }

    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<SignUpResponseModel> {
        return try {
            val request = signUpRequestModel.toSignUpRequest()

            // Tenta com retry automático
            val response = retryWithExponentialBackoff(
                policy = retryPolicy,
                shouldRetry = { exception ->
                    exception is IOException || exception is TimeoutException
                },
                operation = {
                    authApiService.signUp(request)
                }
            )

            if (response.isSuccessful) {
                DefaultResult.Success(response.toApplicationResultModel())
            } else {
                DefaultResult.Error(message = response.message)
            }

        } catch (e: ErrorResponseException) {
            DefaultResult.Error(code = e.error.httpCode.toString(), message = e.error.message)
        } catch (e: Exception) {
            // Mapeia exceção genérica para NetworkError
            val networkError = ErrorMapper.mapThrowableToNetworkError(e)
            DefaultResult.Error(message = networkError.message)
        }
    }
}