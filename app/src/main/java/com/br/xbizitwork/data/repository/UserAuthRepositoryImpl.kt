package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.local.auth.datastore.interfaces.AuthSessionLocalDataSource
import com.br.xbizitwork.data.mappers.toDomainResponse
import com.br.xbizitwork.data.mappers.toDomainResult
import com.br.xbizitwork.data.mappers.toSignInRequestModel
import com.br.xbizitwork.data.mappers.toSignUpRequestModel
import com.br.xbizitwork.data.remote.auth.datasource.UserAuthRemoteDataSource
import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.model.auth.SignInModel
import com.br.xbizitwork.domain.model.auth.SignUpModel
import com.br.xbizitwork.domain.repository.UserAuthRepository
import com.br.xbizitwork.domain.result.auth.SignInResult
import com.br.xbizitwork.domain.result.auth.SignUpResult
import com.br.xbizitwork.domain.session.AuthSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserAuthRemoteDataSource,
    private val localDataSource: AuthSessionLocalDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
): UserAuthRepository {

    override suspend fun signIn(signInModel: SignInModel): DomainDefaultResult<SignInResult> =
        withContext(coroutineDispatcherProvider.io()) {
            val loginRequest = signInModel.toSignInRequestModel()
            val result = remoteDataSource.signIn(loginRequest)

            when (result) {
                is DefaultResult.Success -> {
                    val domainResponse = result.data.toDomainResponse()
                    DomainDefaultResult.Success(domainResponse)
                }

                is DefaultResult.Error -> {
                    DomainDefaultResult.Error(message = result.message)
                }
            }
        }

    override suspend fun signUp(signUpModel: SignUpModel): DomainDefaultResult<SignUpResult> =
        withContext(coroutineDispatcherProvider.io()) {
            val sigUpnRequest = signUpModel.toSignUpRequestModel()
            val result = remoteDataSource.signUp(sigUpnRequest)

            when (result) {
                is DefaultResult.Success -> {
                    val domainResponse = result.data.toDomainResult()
                    DomainDefaultResult.Success(domainResponse)
                }

                is DefaultResult.Error -> {
                    DomainDefaultResult.Error(message = result.message)
                }
            }
        }

    override fun observeSession(): Flow<AuthSession> =
        localDataSource.observeSession()
            .flowOn(coroutineDispatcherProvider.io())

    override suspend fun saveSession(
        name: String,
        email: String,
        token: String,
    ) = withContext(coroutineDispatcherProvider.io()) {
        localDataSource.saveSession(
            name = name,
            email = email,
            token = token
        )
    }

    override suspend fun getSession(): AuthSession? =
        withContext(coroutineDispatcherProvider.io()) {
            localDataSource.getSession()
        }

    override suspend fun clearSession() =
        withContext(coroutineDispatcherProvider.io()) {
            localDataSource.clearSession()
        }
}