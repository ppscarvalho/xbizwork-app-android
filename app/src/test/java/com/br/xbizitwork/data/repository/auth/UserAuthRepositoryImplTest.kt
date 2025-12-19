package com.br.xbizitwork.data.repository.auth

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.local.auth.datastore.AuthSessionLocalDataSource
import com.br.xbizitwork.data.remote.auth.datasource.UserAuthRemoteDataSource
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel
import com.br.xbizitwork.data.repository.UserAuthRepositoryImpl
import com.br.xbizitwork.domain.model.auth.SignInModel
import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.session.AuthSession
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher

/**
 * Testes unitários para UserAuthRepositoryImpl.
 *
 * Testa:
 * - Coordenação entre remote e local datasources
 * - Mapeamento de resultados
 * - Salvamento de sessão após login bem-sucedido
 * - Tratamento de erros
 */
class UserAuthRepositoryImplTest {

    @Mock
    private lateinit var mockRemoteDataSource: UserAuthRemoteDataSource

    @Mock
    private lateinit var mockLocalDataSource: AuthSessionLocalDataSource

    private lateinit var repository: UserAuthRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        
        // Cria um CoroutineDispatcherProvider mock simples
        val mockDispatcherProvider = object : CoroutineDispatcherProvider {
            override fun io() = kotlinx.coroutines.Dispatchers.Unconfined
            override fun main() = kotlinx.coroutines.Dispatchers.Unconfined
            override fun default() = kotlinx.coroutines.Dispatchers.Unconfined
        }

        repository = UserAuthRepositoryImpl(
            remoteDataSource = mockRemoteDataSource,
            localDataSource = mockLocalDataSource,
            coroutineDispatcherProvider = mockDispatcherProvider
        )
    }

    @Test
    fun signIn_withValidCredentials_returnsDomainSuccess() = runTest {
        // Arrange
        val signInModel = SignInModel(
            email = "test@example.com",
            password = "Password123!"
        )

        val mockRemoteResult = DefaultResult.Success(
            data = SignInResponseModel(
                name = "Test User",
                email = "test@example.com",
                token = "mock_token_123",
                isSuccessful = true,
                message = "Login successful"
            )
        )

        whenever(mockRemoteDataSource.signIn(any())).thenReturn(mockRemoteResult)

        // Act
        val result = repository.signIn(signInModel)

        // Assert
        assertThat(result).isInstanceOf(DomainDefaultResult.Success::class.java)
        
        // Verifica que o remote datasource foi chamado
        verify(mockRemoteDataSource).signIn(any())
    }

    @Test
    fun signIn_withRemoteError_returnsDomainError() = runTest {
        // Arrange
        val signInModel = SignInModel(
            email = "test@example.com",
            password = "wrongpassword"
        )

        val mockRemoteResult = DefaultResult.Error(
            code = "401",
            message = "Unauthorized"
        )

        whenever(mockRemoteDataSource.signIn(any())).thenReturn(mockRemoteResult)

        // Act
        val result = repository.signIn(signInModel)

        // Assert
        assertThat(result).isInstanceOf(DomainDefaultResult.Error::class.java)
        
        if (result is DomainDefaultResult.Error) {
            assertThat(result.message).contains("Unauthorized")
        }
    }

    @Test
    fun saveSession_callsLocalDataSource() = runTest {
        // Arrange
        val name = "John Doe"
        val email = "test@example.com"
        val token = "jwt_token"

        // Act
        repository.saveSession(name, email, token)

        // Assert
        verify(mockLocalDataSource).saveSession(
            name = name,
            email = email,
            token = token
        )
    }

    @Test
    fun clearSession_callsLocalDataSource() = runTest {
        // Act
        repository.clearSession()

        // Assert
        verify(mockLocalDataSource).clearSession()
    }
}
