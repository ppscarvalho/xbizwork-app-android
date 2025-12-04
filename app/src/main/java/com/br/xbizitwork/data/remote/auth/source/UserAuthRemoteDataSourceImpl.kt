package com.br.xbizitwork.data.remote.auth.source

import com.br.xbizitwork.core.exceptions.ErrorResponseException
import com.br.xbizitwork.core.mappers.toModel
import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.auth.api.UserAuthApiService
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequestModel
import com.br.xbizitwork.data.remote.auth.mappers.toSignInRequest
import com.br.xbizitwork.data.remote.auth.mappers.toSignInResponseModel
import com.br.xbizitwork.data.remote.auth.mappers.toSignUpRequest
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import javax.inject.Inject

class UserAuthRemoteDataSourceImpl @Inject constructor(
    private val authApiService: UserAuthApiService
): UserAuthRemoteDataSource {
    override suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<SignInResponseModel> {
        return try {
            var request = signInRequestModel.toSignInRequest()
            var response = authApiService.signIn(request)

            if(response.isSuccessful){
                DefaultResult.Success(response.toSignInResponseModel())
            }else{
                DefaultResult.Error(message = response.message)
            }
        }catch (e: ErrorResponseException){
            DefaultResult.Error(code = e.error.httpCode.toString(), message = e.error.message)
        }catch (e: Exception){
            DefaultResult.Error(message = e.message.toString())
        }
    }

    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApiResultModel> {
        return try {
            var request = signUpRequestModel.toSignUpRequest()
            var response = authApiService.signUp(request)

            if(response.isSuccessful){
                DefaultResult.Success(response.toModel())
            }else{
                DefaultResult.Error(message = response.message)
            }
        }catch (e: RedirectResponseException){
            DefaultResult.Error(e.response.status.value.toString(), e.response.status.description)
        }catch (e: ClientRequestException){
            DefaultResult.Error(e.response.status.value.toString(), e.response.status.description)
        }catch (e: ErrorResponseException){
            DefaultResult.Error(code = e.error.httpCode.toString(), message = e.error.message)
        }catch (e: Exception){
            DefaultResult.Error(message = e.message.toString())
        }
    }
}