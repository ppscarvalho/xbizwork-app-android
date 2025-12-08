package com.br.xbizitwork.data.remote.auth.datasource.implementations

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.auth.api.UserAuthApiService
import com.br.xbizitwork.data.remote.auth.datasource.UserAuthRemoteDataSourceImpl
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever
import java.util.concurrent.TimeoutException

/**
 * Testes unitários para UserAuthRemoteDataSourceImpl.
 *
 * Testa:
 * - Sucesso na requisição signIn
 * - Falha na requisição signIn
 * - Retry automático em falhas de rede
 * - Mapeamento de respostas
 */
class UserAuthRemoteDataSourceImplTest {

    @Mock
    private lateinit var mockAuthApiService: UserAuthApiService

    private lateinit var dataSource: UserAuthRemoteDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        dataSource = UserAuthRemoteDataSourceImpl(mockAuthApiService)
    }

    @Test
    fun signIn_withValidRequest_returnsSuccess() = runTest {
        // Arrange
        val signInRequest = SignInRequestModel(
            email = "test@example.com",
            password = "Password123!"
        )
        
        val mockResponse = SignInResponse(
            name = "John Doe",
            email = "test@example.com",
            token = "fake_jwt_token",
            isSuccessful = true,
            message = "Login successful"
        )

        whenever(mockAuthApiService.signIn(any())).thenReturn(mockResponse)

        // Act
        val result = dataSource.signIn(signInRequest)

        // Assert
        assertThat(result).isInstanceOf(DefaultResult.Success::class.java)
        
        if (result is DefaultResult.Success) {
            assertThat(result.data.name).isEqualTo("John Doe")
            assertThat(result.data.email).isEqualTo("test@example.com")
            assertThat(result.data.token).isEqualTo("fake_jwt_token")
        }
    }

    @Test
    fun signIn_withFailedResponse_returnsError() = runTest {
        // Arrange
        val signInRequest = SignInRequestModel(
            email = "test@example.com",
            password = "wrongpassword"
        )
        
        val mockResponse = SignInResponse(
            name = "",
            email = "",
            token = "",
            isSuccessful = false,
            message = "Invalid credentials"
        )

        whenever(mockAuthApiService.signIn(any())).thenReturn(mockResponse)

        // Act
        val result = dataSource.signIn(signInRequest)

        // Assert
        assertThat(result).isInstanceOf(DefaultResult.Error::class.java)
        
        if (result is DefaultResult.Error) {
            assertThat(result.message).contains("Invalid credentials")
        }
    }

    @Test
    fun signIn_withNetworkTimeout_retriesAndEventuallyFails() = runTest {
        // Arrange
        val signInRequest = SignInRequestModel(
            email = "test@example.com",
            password = "Password123!"
        )

        whenever(mockAuthApiService.signIn(any()))
            .thenAnswer { throw TimeoutException("Request timeout") }

        // Act
        val result = dataSource.signIn(signInRequest)

        // Assert - Deve fazer retry e retornar erro
        assertThat(result).isInstanceOf(DefaultResult.Error::class.java)
    }
}
