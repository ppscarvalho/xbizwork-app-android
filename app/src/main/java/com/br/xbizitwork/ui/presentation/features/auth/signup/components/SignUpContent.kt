package com.br.xbizitwork.ui.presentation.features.auth.signup.components

import android.content.res.Configuration
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.icons.AppIcon
import com.br.xbizitwork.ui.presentation.features.auth.signup.state.SignUpState
import com.br.xbizitwork.ui.theme.BeigeBackground
import com.br.xbizitwork.ui.theme.GrayText
import com.br.xbizitwork.ui.theme.TealPrimary
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily
import com.br.xbizitwork.R


@Composable
fun SignUpContent(
    modifier: Modifier = Modifier,
    uiState: SignUpState,
    paddingValues: PaddingValues,
    onNavigateToSignInScreen: () -> Unit,
    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onSignUpClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
    ) {
        // Header curvo com fundo bege
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(BeigeBackground)
        ) {
            AppIcon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 20.dp)
            )
        }

        // Conteúdo scrollável
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 180.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Título
            Text(
                text = stringResource(R.string.signUp_text),
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center,
                color = TealPrimary,
                modifier = Modifier.fillMaxWidth()
            )

            // Mensagem de erro (se houver)
            if (!uiState.signUpErrorMessage.isNullOrEmpty() || !uiState.fieldErrorMessage.isNullOrEmpty()) {
                Text(
                    text = uiState.signUpErrorMessage ?: uiState.fieldErrorMessage.orEmpty(),
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFFD32F2F),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Formulário
            SignUpContainer(
                modifier = Modifier.fillMaxWidth(),
                isLoading = uiState.isLoading,
                buttonEnabled = uiState.isFormValid,
                nameValue = uiState.name,
                emailValue = uiState.email,
                passwordValue = uiState.password,
                confirmPasswordValue = uiState.confirmPassword,
                onNameChanged = onNameChanged,
                onEmailChanged = onEmailChanged,
                onPasswordChanged = onPasswordChanged,
                onConfirmPasswordChanged = onConfirmPasswordChanged,
                onSignUpClick = onSignUpClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Link para login
            Row(
                modifier = Modifier.padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.already_have_an_account),
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    color = GrayText
                )

                Text(
                    text = stringResource(R.string.login_text),
                    fontSize = 14.sp,
                    fontFamily = poppinsFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = TealPrimary,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable { onNavigateToSignInScreen() }
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SignUpContentPreview() {
    XBizWorkTheme {
        SignUpContent(
            modifier = Modifier.fillMaxSize(),
            uiState = SignUpState(
                name = "Pedro Carvalho",
                email = "ppscarvalho@gmail.com",
                password = "plutao@2025",
                confirmPassword = "plutao@2025"),
            paddingValues = PaddingValues(),
            onNavigateToSignInScreen = {},
            onNameChanged = {},
            onEmailChanged = {},
            onPasswordChanged = {},
            onConfirmPasswordChanged = {},
            onSignUpClick = {}
        )
    }
}