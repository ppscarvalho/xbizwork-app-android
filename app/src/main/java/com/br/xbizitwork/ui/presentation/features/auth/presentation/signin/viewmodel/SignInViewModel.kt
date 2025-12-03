package com.br.xbizitwork.ui.presentation.features.auth.presentation.signin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.config.Constants
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignInRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignInResultValidation
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.SignInUseCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.ValidateSignInUseCase
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signin.events.SignInEvent
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signin.state.SignInState
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
    private val validateSignInUseCase: ValidateSignInUseCase
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
                    SignInRequestModel(
                        email = _uiState.value.email.trim(),
                        password = _uiState.value.password.trim(),
                    )
                )
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = {response ->
                    _uiState.update {
                        it.copy(isLoading = false, isSuccess = response.isSuccessful )
                    }
                    _sideEffectChannel.send(SideEffect.ShowToast(response.message.toString()))
                },
                onFailure = {error ->
                    _uiState.update {
                        it.copy(isLoading = false, signUpErrorMessage = error.message.toString())
                    }
                }
            )
        }
    }

    private fun validateForm() {
        val validationResult = validateSignInUseCase(
            email = _uiState.value.email,
            password = _uiState.value.password,
        )
        validateSignInData(validationResult)
    }

    private fun validateSignInData(type: SignInResultValidation) {
        _uiState.update {
            when (type) {
                SignInResultValidation.EmptyField -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.EMPTY_FIELD,
                        isFormValid = false)
                }

                SignInResultValidation.NoEmail -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.INVALID_EMAIL,
                        isFormValid = false)
                }

                SignInResultValidation.PasswordTooShort -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_TOO_SHORT,
                        isFormValid = false)
                }

                SignInResultValidation.PasswordsDoNotMatch -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORDS_DO_NOT_MATCH,
                        isFormValid = false)
                }

                SignInResultValidation.PasswordUpperCaseMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_UPPERCASE_MISSING,
                        isFormValid = false)
                }

                SignInResultValidation.PasswordSpecialCharMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_SPECIAL_CHAR_MISSING,
                        isFormValid = false)
                }

                SignInResultValidation.PasswordNumberMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_NUMBER_MISSING,
                        isFormValid = false)
                }

                SignInResultValidation.Valid -> {
                    it.copy(
                        fieldErrorMessage = "Dados validados com sucesso",
                        isFormValid = true)
                }
            }
        }
    }
}