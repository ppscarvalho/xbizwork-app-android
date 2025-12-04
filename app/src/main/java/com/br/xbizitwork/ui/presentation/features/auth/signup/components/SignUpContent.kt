package com.br.xbizitwork.ui.presentation.features.auth.signup.components

import android.content.res.Configuration
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.icons.AppIcon
import com.br.xbizitwork.ui.presentation.features.auth.signup.state.SignUpState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFOntFamily
import com.example.xbizitwork.R

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
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
    ) {
        AppIcon(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 140.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.signUp_text),
                    fontFamily = poppinsFOntFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text =uiState.signUpErrorMessage ?: uiState.fieldErrorMessage.orEmpty(),
                    fontFamily = poppinsFOntFamily,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp))
            }

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
            Spacer(modifier = Modifier.height(14.dp))

            Row(
                modifier = Modifier.padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = stringResource(R.string.already_have_an_account),
                    fontSize = 16.sp,
                    fontFamily = poppinsFOntFamily,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = stringResource(R.string.login_text),
                    fontSize = 16.sp,
                    fontFamily = poppinsFOntFamily,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(start = 5.dp)
                        .clickable() { onNavigateToSignInScreen() }
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
            modifier = Modifier.fillMaxSize().padding(10.dp),
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