package com.br.xbizitwork.ui.presentation.features.auth.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.domain.usecase.auth.signin.SignInUseCase
import com.br.xbizitwork.core.config.Constants
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.validations.auth.SignInValidationError
import com.br.xbizitwork.domain.model.auth.SignInModel
import com.br.xbizitwork.domain.usecase.auth.signin.ValidateSignInUseCase
import com.br.xbizitwork.domain.usecase.session.SaveAuthSessionUseCase
import com.br.xbizitwork.ui.presentation.features.auth.signin.events.SignInEvent
import com.br.xbizitwork.ui.presentation.features.auth.signin.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val validateSignInUseCase: ValidateSignInUseCase,
    private val saveAuthSessionUseCase: SaveAuthSessionUseCase,
): ViewModel() {

    private val _uiState: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    val uiState: StateFlow<SignInState> = _uiState.asStateFlow()

    private val _sideEffectChannel = Channel<SideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _sideEffectChannel.receiveAsFlow()

    fun onEvent(event: SignInEvent){
        when(event){
            SignInEvent.OnSignInClick -> onSignUpClick()
        }
    }

    fun onEmailChange(value: String){
        _uiState.update { it.copy(email = value) }
        validateForm()
    }

    fun onPasswordChange(value: String){
        _uiState.update { it.copy(password = value) }
        validateForm()
    }

    fun onSignUpClick(){
        viewModelScope.launch {
            signInUseCase.invoke(
                parameters = SignInUseCase.Parameters(
                    SignInModel(
                        email = _uiState.value.email.trim(),
                        password = _uiState.value.password.trim(),
                    )
                )
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = {response ->
                    logInfo("SIGN_IN_SUCCESS", "Response recebido: name=${response.name}, email=${response.email}, token=${response.token}")
                    _uiState.update {
                        it.copy(isLoading = false, isSuccess = response.isSuccessful )
                    }
                    _sideEffectChannel.send(SideEffect.ShowToast(response.message.toString()))
                    // ✅ CORRIGIDO: Verificar valores nulos antes de converter
                    if (!response.name.isNullOrEmpty() && !response.email.isNullOrEmpty() && !response.token.isNullOrEmpty()) {
                        saveLocalSession(response.name!!, response.email!!, response.token!!)
                    } else {
                        logInfo("SIGN_IN_ERROR", "Dados vazios recebidos: name=${response.name}, email=${response.email}, token=${response.token}")
                        _sideEffectChannel.send(SideEffect.ShowToast("Erro: Dados vazios recebidos do servidor"))
                    }
                },
                onFailure = {error ->
                    _uiState.update {
                        it.copy(isLoading = false, signUpErrorMessage = error.message.toString())
                    }
                }
            )
        }
    }

    private suspend fun saveLocalSession(name: String, email: String, token: String) {
        logInfo("SAVE_SESSION", "Salvando sessão: name=$name, email=$email, token=$token")
        saveAuthSessionUseCase.invoke(
            SaveAuthSessionUseCase.Parameters(
                name = name,
                email = email,
                token = token)
        ).collectUiState(
            onLoading = {/*NO-OP*/},
            onSuccess = {
                logInfo("SAVE_SESSION", "Sessão salva com sucesso!")
            },
            onFailure = {error ->
                logInfo("SAVE_SESSION", "Erro ao salvar sessão: ${error.message}")
            }
        )
    }

    private fun validateForm() {
        val validationResult = validateSignInUseCase(
            email = _uiState.value.email,
            password = _uiState.value.password,
        )
        validateSignInData(validationResult)
    }

    private fun validateSignInData(type: SignInValidationError) {
        _uiState.update {
            when (type) {
                SignInValidationError.EmptyField -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.EMPTY_FIELD,
                        isFormValid = false)
                }

                SignInValidationError.NoEmail -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.INVALID_EMAIL,
                        isFormValid = false)
                }

                SignInValidationError.PasswordTooShort -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_TOO_SHORT,
                        isFormValid = false)
                }

                SignInValidationError.PasswordsDoNotMatch -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORDS_DO_NOT_MATCH,
                        isFormValid = false)
                }

                SignInValidationError.PasswordUpperCaseMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_UPPERCASE_MISSING,
                        isFormValid = false)
                }

                SignInValidationError.PasswordSpecialCharMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_SPECIAL_CHAR_MISSING,
                        isFormValid = false)
                }

                SignInValidationError.PasswordNumberMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_NUMBER_MISSING,
                        isFormValid = false)
                }

                SignInValidationError.Valid -> {
                    it.copy(
                        fieldErrorMessage = "Dados validados com sucesso",
                        isFormValid = true)
                }
            }
        }
    }
}