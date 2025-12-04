package com.br.xbizitwork.ui.presentation.features.auth.data.repository

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.ui.presentation.features.auth.data.local.datastore.AuthSessionLocalDataSource
import com.br.xbizitwork.ui.presentation.features.auth.data.local.model.AuthSession
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.response.SignInResponseModel
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignInRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignUpRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.repository.UserAuthRepository
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.source.UserAuthRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserAuthRemoteDataSource,
    private val localDataSource: AuthSessionLocalDataSource
): UserAuthRepository {
    override suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<SignInResponseModel> {
        return remoteDataSource.signIn(signInRequestModel)
    }

    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApiResultModel> {
        return remoteDataSource.signUp(signUpRequestModel)
    }

    override fun observeSession(): Flow<AuthSession> = localDataSource.observeSession()

    override suspend fun saveSession(
        name: String,
        token: String,
        email: String,
    ) {
        return localDataSource.saveSession(
            name = name,
            token = token,
            email = email)
    }

    override suspend fun getSession(): AuthSession?  = localDataSource.getSession()

    override suspend fun clearSession() = localDataSource.clearSession()
}