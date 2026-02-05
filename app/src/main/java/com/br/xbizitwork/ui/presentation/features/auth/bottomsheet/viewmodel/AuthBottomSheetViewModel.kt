package com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.auth.SignInModel
import com.br.xbizitwork.domain.usecase.auth.signin.SignInUseCase
import com.br.xbizitwork.domain.usecase.session.SaveAuthSessionUseCase
import com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.events.AuthBottomSheetEvent
import com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.state.AuthBottomSheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel do BottomSheet de autenticação inline
 * Segue o padrão do SignInViewModel (reutiliza mesmos UseCases)
 */
@HiltViewModel
class AuthBottomSheetViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val saveAuthSessionUseCase: SaveAuthSessionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthBottomSheetState())
    val uiState: StateFlow<AuthBottomSheetState> = _uiState.asStateFlow()

    private val _sideEffectChannel = Channel<AppSideEffect>(capacity = Channel.BUFFERED)
    val sideEffectChannel = _sideEffectChannel.receiveAsFlow()

    fun onEvent(event: AuthBottomSheetEvent) {
        when (event) {
            AuthBottomSheetEvent.OnLoginClick -> onLoginClick()
            AuthBottomSheetEvent.OnDismiss -> onDismiss()
            AuthBottomSheetEvent.OnTogglePasswordVisibility -> togglePasswordVisibility()
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value, errorMessage = "") }
        validateForm()
    }

    fun onPasswordChange(value: String) {
        _uiState.update { it.copy(password = value, errorMessage = "") }
        validateForm()
    }

    private fun validateForm() {
        val isValid = _uiState.value.email.isNotBlank() &&
                _uiState.value.password.isNotBlank()
        _uiState.update { it.copy(isFormValid = isValid) }
    }

    private fun togglePasswordVisibility() {
        _uiState.update { it.copy(passwordVisible = !it.passwordVisible) }
    }

    private fun onLoginClick() {
        if (!_uiState.value.isFormValid) {
            _uiState.update { it.copy(errorMessage = "Preencha todos os campos") }
            return
        }

        viewModelScope.launch {
            signInUseCase.invoke(
                parameters = SignInUseCase.Parameters(
                    SignInModel(
                        email = _uiState.value.email.trim(),
                        password = _uiState.value.password.trim()
                    )
                )
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { response ->
                    logInfo("AUTH_BOTTOMSHEET_VM", "✅ Login bem-sucedido: ${response.name}")

                    val userId = response.id ?: 0
                    val userName = response.name ?: ""
                    val userEmail = response.email ?: ""
                    val userToken = response.token ?: ""

                    saveLocalSession(
                        userId,
                        userName,
                        userEmail,
                        userToken
                    )
                },
                onFailure = { error ->
                    logInfo("AUTH_BOTTOMSHEET_VM", "❌ Erro no login: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Erro ao fazer login"
                        )
                    }
                }
            )
        }
    }

    private suspend fun saveLocalSession(
        id: Int,
        name: String,
        email: String,
        token: String
    ) {
        logInfo("AUTH_BOTTOMSHEET_VM", "💾 Salvando sessão...")

        saveAuthSessionUseCase.invoke(
            SaveAuthSessionUseCase.Parameters(
                id = id,
                name = name,
                email = email,
                token = token
            )
        ).collectUiState(
            onLoading = { /* NO-OP */ },
            onSuccess = {
                logInfo("AUTH_BOTTOMSHEET_VM", "✅ Sessão salva com sucesso!")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true
                    )
                }
                _sideEffectChannel.send(AppSideEffect.ShowToast("Login realizado com sucesso!"))
            },
            onFailure = { error ->
                logInfo("AUTH_BOTTOMSHEET_VM", "❌ Erro ao salvar sessão: ${error.message}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Erro ao salvar sessão"
                    )
                }
            }
        )
    }

    private fun onDismiss() {
        // Reset state quando fechar
        _uiState.update { AuthBottomSheetState() }
    }
}
