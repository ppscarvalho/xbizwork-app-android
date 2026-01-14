package com.br.xbizitwork.ui.presentation.features.menu.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.MobileScreenShare
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.buttons.MenuButton
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun MenuContainer(
    modifier: Modifier = Modifier,
    onClickUpdateProfile: () -> Unit,
    onClickChangePassword: () -> Unit,
    onClickCreateSkills: () -> Unit,
    onClickSetupSchedule: () -> Unit,
    onClickYourPlan: () -> Unit,
    onClickMyAppointments: () -> Unit,
    onClickProfessionalAgenda: () -> Unit,
    onClickFAQ: () -> Unit,
    onClickAppVersion: () -> Unit,
    onClickRateApp: () -> Unit,
    onClickLogout: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            // ========== SEÇÃO PERFIL ==========
            item {
                Text(
                    text = "Perfil",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
            item {
                Spacer(modifier = Modifier.fillMaxSize())
            }

            item {
                MenuButton(
                    leftIcon = Icons.Filled.Person,
                    text = "Alterar Perfil",
                    onClick = onClickUpdateProfile

                )
            }

            // ========== SEÇÃO SERVIÇO ==========
            item {
                Text(
                    text = "Serviço",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.Filled.Key,
                    text = "Alterar Senha",
                    onClick = onClickChangePassword
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.Filled.Psychology,
                    text = "Minhas habilidades",
                    onClick = onClickCreateSkills
                )
            }

//            item {
//                MenuButton(
//                    leftIcon = Icons.Filled.DateRange,
//                    text = "Monte sua agenda",
//                    onClick = onClickSetupSchedule
//                )
//            }

            item {
                MenuButton(
                    leftIcon = Icons.AutoMirrored.Filled.Assignment,
                    text = "Planos",
                    onClick = onClickYourPlan
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.Filled.Event,
                    text = "Meus compromissos",
                    onClick = onClickMyAppointments
                )
            }

//            item {
//                MenuButton(
//                    leftIcon = Icons.Filled.ViewModule,
//                    text = "Agenda profissional",
//                    onClick = onClickProfessionalAgenda,
//                    hasDividerAfter = false
//                )
//            }

            item {
                Spacer(modifier = Modifier.fillMaxSize())
            }

            // ========== SEÇÃO SOBRE O APP ==========
            item {
                Text(
                    text = "Sobre o app",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.AutoMirrored.Filled.Help,
                    text = "Dúvidas frequentes",
                    onClick = onClickFAQ
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.Filled.MobileScreenShare,
                    text = "Versão do aplicativo",
                    onClick = onClickAppVersion
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.Filled.Star,
                    text = "Avalie nosso aplicativo",
                    onClick = onClickRateApp
                )
            }

            item {
                Spacer(modifier = Modifier.fillMaxSize())
            }

            // ========== SEÇÃO GERENCIAR O APP ==========
            item {
                Text(
                    text = "Gerenciar o App",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.AutoMirrored.Filled.ExitToApp,
                    text = "Sair",
                    onClick = onClickLogout,
                    hasDividerAfter = false,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0f344e)
@Composable
private fun MenuContainerPreView() {
    XBizWorkTheme {
        MenuContainer(
            modifier = Modifier.fillMaxSize(),
            onClickUpdateProfile = {},
            onClickChangePassword = {},
            onClickCreateSkills={},
            onClickSetupSchedule = {},
            onClickYourPlan = {},
            onClickMyAppointments = {},
            onClickProfessionalAgenda = {},
            onClickFAQ = {},
            onClickAppVersion = {},
            onClickRateApp = {},
            onClickLogout = {}
        )
    }
}