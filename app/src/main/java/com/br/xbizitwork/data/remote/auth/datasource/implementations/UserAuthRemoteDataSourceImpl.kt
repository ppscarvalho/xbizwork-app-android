package com.br.xbizitwork.data.remote.auth.datasource.implementations

import com.br.xbizitwork.application.mappers.toLoginRequest
import com.br.xbizitwork.application.mappers.toLoginResponseModel
import com.br.xbizitwork.application.mappers.toSignUpRequest
import com.br.xbizitwork.application.request.SignInRequestModel
import com.br.xbizitwork.application.response.ApplicationResponseModel
import com.br.xbizitwork.application.response.ApplicationResultModel
import com.br.xbizitwork.core.exceptions.ErrorResponseException
import com.br.xbizitwork.core.mappers.toApplicationResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.auth.api.UserAuthApiService
import com.br.xbizitwork.data.remote.auth.datasource.interfaces.UserAuthRemoteDataSource
import com.br.xbizitwork.application.request.SignUpRequestModel
import javax.inject.Inject

class UserAuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: UserAuthApiService
): UserAuthRemoteDataSource {

    override suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<ApplicationResponseModel> {
        return try {
            val request = signInRequestModel.toLoginRequest() // mapper p/ DTO da API
            val response = authApiService.signIn(request)

            if (response.isSuccessful) {
                DefaultResult.Success(response.toLoginResponseModel())
            } else {
                DefaultResult.Error(message = response.message)
            }

        } catch (e: ErrorResponseException) {
            DefaultResult.Error(code = e.error.httpCode.toString(), message = e.error.message)

        } catch (e: Exception) {
            DefaultResult.Error(message = e.message.toString())
        }
    }

    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApplicationResultModel> {
        return try {
            val request = signUpRequestModel.toSignUpRequest() // mapper p/ DTO da API
            val response = authApiService.signUp(request)

            if (response.isSuccessful) {
                DefaultResult.Success(response.toApplicationResultModel())
            } else {
                DefaultResult.Error(message = response.message)
            }

        } catch (e: ErrorResponseException) {
            DefaultResult.Error(code = e.error.httpCode.toString(), message = e.error.message)

        } catch (e: Exception) {
            DefaultResult.Error(message = e.message.toString())
        }
    }
}
