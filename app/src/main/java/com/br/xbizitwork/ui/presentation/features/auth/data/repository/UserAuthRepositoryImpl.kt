package com.br.xbizitwork.ui.presentation.features.auth.data.repository

import com.br.xbizitwork.core.data.remote.auth.response.TokenResponse
import com.br.xbizitwork.core.domain.model.ApiResultModel
import com.br.xbizitwork.core.util.common.DefaultResult
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignInRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignUpRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.repository.UserAuthRepository
import com.br.xbizitwork.ui.presentation.features.auth.domain.source.UserAuthRemoteDataSource
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserAuthRemoteDataSource
): UserAuthRepository {
    override suspend fun signIn(signInRequestModel: SignInRequestModel): TokenResponse {
        return remoteDataSource.signIn(signInRequestModel)
    }

    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApiResultModel> {
        return remoteDataSource.signUp(signUpRequestModel)
    }
}