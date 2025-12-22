package com.br.xbizitwork.ui.presentation.features.auth.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.domain.usecase.auth.signin.SignInUseCase
import com.br.xbizitwork.core.config.Constants
import com.br.xbizitwork.core.sideeffects.AppSideEffect
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

    private val _App_sideEffectChannel = Channel<AppSideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _App_sideEffectChannel.receiveAsFlow()

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
                    logInfo("SIGN_IN_SUCCESS", "✅ Response recebido:")
                    logInfo("SIGN_IN_SUCCESS", "  - id: ${response.id} (type: ${response.id?.javaClass?.simpleName})")
                    logInfo("SIGN_IN_SUCCESS", "  - name: '${response.name}' (empty: ${response.name.isNullOrEmpty()})")
                    logInfo("SIGN_IN_SUCCESS", "  - email: '${response.email}' (empty: ${response.email.isNullOrEmpty()})")
                    logInfo("SIGN_IN_SUCCESS", "  - token: '${response.token?.take(20)}...' (empty: ${response.token.isNullOrEmpty()})")
                    logInfo("SIGN_IN_SUCCESS", "  - isSuccessful: ${response.isSuccessful}")

                    _uiState.update {
                        it.copy(isLoading = false, isSuccess = response.isSuccessful )
                    }
                    _App_sideEffectChannel.send(AppSideEffect.ShowToast(response.message.toString()))

                    // Salvar sessão com os dados recebidos
                    val userId = response.id ?: 0
                    val userName = response.name.orEmpty()
                    val userEmail = response.email.orEmpty()
                    val userToken = response.token.orEmpty()

                    logInfo("SIGN_IN_SESSION", "💾 Salvando sessão:")
                    logInfo("SIGN_IN_SESSION", "  - userId: $userId")
                    logInfo("SIGN_IN_SESSION", "  - userName: '$userName'")
                    logInfo("SIGN_IN_SESSION", "  - userEmail: '$userEmail'")
                    logInfo("SIGN_IN_SESSION", "  - userToken: '${userToken.take(20)}...'")

                    saveLocalSession(userId, userName, userEmail, userToken)
                },
                onFailure = {error ->
                    _uiState.update {
                        it.copy(isLoading = false, signUpErrorMessage = error.message.toString())
                    }
                }
            )
        }
    }

    private suspend fun saveLocalSession(id: Int, name: String, email: String, token: String) {
        logInfo("SAVE_SESSION", "Salvando sessão: id=$id, name=$name, email=$email, token=$token")
        saveAuthSessionUseCase.invoke(
            SaveAuthSessionUseCase.Parameters(
                id = id,
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