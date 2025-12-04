package com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.config.Constants
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignUpRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignUpResultValidation
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.SignUpUseCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.ValidateSignUpUseCase
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.events.SignUpEvent
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.state.SignUpState
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
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val validateSignUpUseCase: ValidateSignUpUseCase
): ViewModel() {

    private val _uiState: MutableStateFlow<SignUpState> = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    private val _sideEffectChannel = Channel<SideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _sideEffectChannel.receiveAsFlow()

    fun onEvent(event: SignUpEvent){
        when(event){
            SignUpEvent.OnSignUpClick -> onSignUpClick()
        }
    }

    fun onNameChange(value: String){
        _uiState.update { it.copy(name = value) }
        validateForm()
    }

    fun onEmailChange(value: String){
        _uiState.update { it.copy(email = value) }
        validateForm()
    }

    fun onPasswordChange(value: String){
        _uiState.update { it.copy(password = value) }
        validateForm()
    }

    fun onConfirmPasswordChange(value: String){
        _uiState.update { it.copy(confirmPassword =  value) }
        validateForm()
    }

    fun onSignUpClick(){
        viewModelScope.launch {
            signUpUseCase.invoke(
                parameters = SignUpUseCase.Parameters(
                    SignUpRequestModel(
                        name = _uiState.value.name.trim(),
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
                    _sideEffectChannel.send(SideEffect.ShowToast(response.message))
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
        val validationResult = validateSignUpUseCase(
            name = _uiState.value.name,
            email = _uiState.value.email,
            password = _uiState.value.password,
            confirmPassword = _uiState.value.confirmPassword
        )
        validateSignUpData(validationResult)
    }

    private fun validateSignUpData(type: SignUpResultValidation) {
        _uiState.update {
            when (type) {
                SignUpResultValidation.EmptyField -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.EMPTY_FIELD,
                        isFormValid = false)
                }

                SignUpResultValidation.NoEmail -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.INVALID_EMAIL,
                        isFormValid = false)
                }

                SignUpResultValidation.PasswordTooShort -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_TOO_SHORT,
                        isFormValid = false)
                }

                SignUpResultValidation.PasswordsDoNotMatch -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORDS_DO_NOT_MATCH,
                        isFormValid = false)
                }

                SignUpResultValidation.PasswordUpperCaseMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_UPPERCASE_MISSING,
                        isFormValid = false)
                }

                SignUpResultValidation.PasswordSpecialCharMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_SPECIAL_CHAR_MISSING,
                        isFormValid = false)
                }

                SignUpResultValidation.PasswordNumberMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_NUMBER_MISSING,
                        isFormValid = false)
                }

                SignUpResultValidation.Valid -> {
                    it.copy(
                        fieldErrorMessage = "Dados validados com sucesso",
                        isFormValid = true)
                }
            }
        }
    }
}