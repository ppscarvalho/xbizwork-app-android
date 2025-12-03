package com.br.xbizitwork.ui.presentation.features.auth.data.source

import com.br.xbizitwork.core.data.remote.auth.UserAuthApiService
import com.br.xbizitwork.core.domain.exceptions.ErrorResponseException
import com.br.xbizitwork.core.domain.mappers.toModel
import com.br.xbizitwork.core.domain.model.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.ui.presentation.features.auth.data.mappers.toSignInRequest
import com.br.xbizitwork.ui.presentation.features.auth.data.mappers.toSignUpRequest
import com.br.xbizitwork.ui.presentation.features.auth.data.mappers.toSignInResponseModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignInResponseModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignInRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignUpRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.source.UserAuthRemoteDataSource
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