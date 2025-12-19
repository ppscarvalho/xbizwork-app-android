package com.br.xbizitwork.ui.presentation.features.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.profile.state.EditProfileUIState
import com.br.xbizitwork.ui.theme.BeigeBackground
import com.br.xbizitwork.ui.theme.GrayText
import com.br.xbizitwork.ui.theme.TealPrimary
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFOntFamily


@Composable
fun EditProfileContent(
    modifier: Modifier = Modifier,
    uiState: EditProfileUIState,
    paddingValues: PaddingValues,
    onNameChanged: (String) -> Unit,
    onCpfChanged: (String) -> Unit,
    onDateOfBirthChanged: (java.time.LocalDate?) -> Unit,
    onGenderChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onZipCodeChanged: (String) -> Unit,
    onZipCodeBlur: () -> Unit,  // ✅ NOVO: Busca endereço quando sai do campo
    onAddressChanged: (String) -> Unit,
    onNumberChanged: (String) -> Unit,
    onNeighborhoodChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    onStateChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
    ) {
        // Header curvo com fundo bege - SEM ÍCONE
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(BeigeBackground)
        )

        // Conteúdo scrollável - EXATAMENTE IGUAL SignUp
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 5.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ✅ LOADING COBRE TODA A TELA!
            if (uiState.isLoading) {
                LoadingIndicator(
                    modifier = Modifier.fillMaxSize(),
                    message = "Carregando perfil..."
                )
            } else {

                // Mensagem de erro (se houver) - EXATAMENTE IGUAL SignUp
                if (!uiState.errorMessage.isNullOrEmpty()) {
                    Text(
                        text = uiState.errorMessage,
                        fontFamily = poppinsFOntFamily,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Formulário - PASSA TODOS OS PARÂMETROS!
                EditProfileContainer(
                    modifier = Modifier.fillMaxWidth(),
                    isLoading = uiState.isLoading,
                    buttonEnabled = uiState.isFormValid,
                    nameValue = uiState.name,
                    cpfValue = uiState.cpf,
                    dateOfBirthValue = uiState.dateOfBirth,
                    genderValue = uiState.gender,
                    emailValue = uiState.email,
                    phoneValue = uiState.phoneNumber,
                    zipCodeValue = uiState.zipCode,
                    addressValue = uiState.address,
                    numberValue = uiState.number,
                    neighborhoodValue = uiState.neighborhood,
                    cityValue = uiState.city,
                    stateValue = uiState.state,
                    onNameChanged = onNameChanged,
                    onCpfChanged = onCpfChanged,
                    onDateOfBirthChanged = onDateOfBirthChanged,
                    onGenderChanged = onGenderChanged,
                    onEmailChanged = onEmailChanged,
                    onPhoneChanged = onPhoneChanged,
                    onZipCodeChanged = onZipCodeChanged,
                    onZipCodeBlur = onZipCodeBlur,  // ✅ Busca endereço quando sai do campo
                    onAddressChanged = onAddressChanged,
                    onNumberChanged = onNumberChanged,
                    onNeighborhoodChanged = onNeighborhoodChanged,
                    onCityChanged = onCityChanged,
                    onStateChanged = onStateChanged,
                    onSaveClick = onSaveClick
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Link para cancelar - EXATAMENTE IGUAL SignUp
                Row(
                    modifier = Modifier.padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Deseja ",
                        fontSize = 14.sp,
                        fontFamily = poppinsFOntFamily,
                        color = GrayText
                    )

                    Text(
                        text = "cancelar?",
                        fontSize = 14.sp,
                        fontFamily = poppinsFOntFamily,
                        fontWeight = FontWeight.Bold,
                        color = TealPrimary,
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable { onCancelClick() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditProfileContentPreview() {
    XBizWorkTheme {
        EditProfileContent(
            uiState = EditProfileUIState(
                name = "João da Silva",
                cpf = "123.456.789-00",
                gender = "M",
                email = "joao@gmail.com",
                phoneNumber = "(11) 98765-4321",
                zipCode = "12345-678",
                city = "São Paulo",
                state = "SP",
                isFormValid = true,
                isLoading = false,
                errorMessage = null
            ),
            paddingValues = PaddingValues(0.dp),
            onNameChanged = {},
            onCpfChanged = {},
            onDateOfBirthChanged = {},
            onGenderChanged = {},
            onEmailChanged = {},
            onPhoneChanged = {},
            onZipCodeChanged = {},
            onZipCodeBlur = {},
            onAddressChanged = {},
            onNumberChanged = {},
            onNeighborhoodChanged = {},
            onCityChanged = {},
            onStateChanged = {},
            onSaveClick = {},
            onCancelClick = {}
        )
    }
}