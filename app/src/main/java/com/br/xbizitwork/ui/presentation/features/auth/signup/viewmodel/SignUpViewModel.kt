package com.br.xbizitwork.ui.presentation.features.auth.signup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.domain.usecase.auth.signup.SignUpUseCase
import com.br.xbizitwork.core.config.Constants
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.domain.validations.auth.SignUpValidationError
import com.br.xbizitwork.domain.model.auth.SignUpModel
import com.br.xbizitwork.domain.usecase.auth.signup.ValidateSignUpUseCase
import com.br.xbizitwork.ui.presentation.features.auth.signup.events.SignUpEvent
import com.br.xbizitwork.ui.presentation.features.auth.signup.state.SignUpState
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

    private val _App_sideEffectChannel = Channel<AppSideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _App_sideEffectChannel.receiveAsFlow()

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
                    SignUpModel(
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
                    _App_sideEffectChannel.send(AppSideEffect.ShowToast(response.message))
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

    private fun validateSignUpData(type: SignUpValidationError) {
        _uiState.update {
            when (type) {
                SignUpValidationError.EmptyField -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.EMPTY_FIELD,
                        isFormValid = false)
                }

                SignUpValidationError.NoEmail -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.INVALID_EMAIL,
                        isFormValid = false)
                }

                SignUpValidationError.PasswordTooShort -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_TOO_SHORT,
                        isFormValid = false)
                }

                SignUpValidationError.PasswordsDoNotMatch -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORDS_DO_NOT_MATCH,
                        isFormValid = false)
                }

                SignUpValidationError.PasswordUpperCaseMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_UPPERCASE_MISSING,
                        isFormValid = false)
                }

                SignUpValidationError.PasswordSpecialCharMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_SPECIAL_CHAR_MISSING,
                        isFormValid = false)
                }

                SignUpValidationError.PasswordNumberMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_NUMBER_MISSING,
                        isFormValid = false)
                }

                SignUpValidationError.Valid -> {
                    it.copy(
                        fieldErrorMessage = "Dados validados com sucesso",
                        isFormValid = true)
                }
            }
        }
    }
}