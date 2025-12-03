package com.br.xbizitwork.ui.presentation.features.user

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Label
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFOntFamily
import io.ktor.sse.SPACE

@Composable
fun UserScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxSize()
            .border(1.dp, color = Color.Red)


    ){
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top =  20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Dados do Usuário",
                fontFamily = poppinsFOntFamily,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),

                value = "Xico da Silva",
                onValueChange = {},
                label = {
                    Text(
                        text = "Nome",
                        color = Color.Red,
                        fontFamily = poppinsFOntFamily
                    )
                },

                placeholder = {
                    Text(
                        text = "Digite seu nome",
                        fontFamily = poppinsFOntFamily,
                        color = colorScheme.onSurfaceVariant,
                    )
                },

                leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Person,
                            contentDescription = "Leading Icon",
                            tint = colorScheme.onSurfaceVariant
                        )
                },
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                onClick = {}
            ) {
                Text(
                    text = "Cadastro"
                )
            }

        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
@Composable
private fun UserScreenPreview() {
    XBizWorkTheme {
        UserScreen()
    }
}