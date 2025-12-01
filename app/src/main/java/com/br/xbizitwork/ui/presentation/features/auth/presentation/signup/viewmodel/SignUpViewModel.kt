package com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.viewmodel

import androidx.lifecycle.ViewModel
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.util.common.Constants
import com.br.xbizitwork.ui.presentation.features.auth.domain.model.SignUpResultValidation
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.SignUpUseCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.ValidateSignUpUseCase
import com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.state.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
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

    fun isPasswordVisible(){
        _uiState.update { it.copy(isPasswordVisible =  !it.isPasswordVisible) }
    }

    fun isConfirmPasswordVisible(){
        _uiState.update { it.copy(isConfirmPasswordVisible =  !it.isConfirmPasswordVisible) }
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
                        isValid = false)
                }

                SignUpResultValidation.NoEmail -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.INVALID_EMAIL,
                        isValid = false)
                }

                SignUpResultValidation.PasswordTooShort -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_TOO_SHORT,
                        isValid = false)
                }

                SignUpResultValidation.PasswordsDoNotMatch -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORDS_DO_NOT_MATCH,
                        isValid = false)
                }

                SignUpResultValidation.PasswordUpperCaseMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_UPPERCASE_MISSING,
                        isValid = false)
                }

                SignUpResultValidation.PasswordSpecialCharMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_SPECIAL_CHAR_MISSING,
                        isValid = false)
                }

                SignUpResultValidation.PasswordNumberMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_NUMBER_MISSING,
                        isValid = false)
                }

                SignUpResultValidation.Valid -> {
                    it.copy(
                        fieldErrorMessage = null,
                        isValid = true)
                }
            }
        }
    }
}