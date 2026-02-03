package com.br.xbizitwork.ui.presentation.components.bottombar

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.outlined.PersonSearch
import com.br.xbizitwork.ui.presentation.components.buttons.MenuButton

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean = false,
    onNavigationToProfileScreen: () -> Unit,
    onNavigationToSearchScreen: () -> Unit,
    onNavigationToFaqScreen: () -> Unit,
    onNavigationToMenuScreen: () -> Unit,
    onNavigationToSearchProfessionalSkillScreen: () -> Unit
) {
    BottomAppBar(
        modifier = modifier
            .height(100.dp),
        containerColor = MaterialTheme.colorScheme.secondary,
        actions = {
            // Row com espaçamento uniforme para distribuir ícones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Conexões (Pessoas)
//                BottomBarItem(
//                    icon = Icons.Outlined.PeopleOutline,
//                    label = "Conexões",
//                    onClick = onNavigationToUsersConnectionScreen
//                )

                // Pesquisar
//                BottomBarItem(
//                    icon = Icons.Outlined.Search,
//                    label = "Pesquisar",
//                    onClick = onNavigationToSearchScreen
//                )

                // Pesquisar
                BottomBarItem(
                    icon = Icons.Outlined.PersonSearch,
                    label = "Pesquisar",
                    onClick = onNavigationToSearchProfessionalSkillScreen
                )

                // Dúvidas Frequentes
                BottomBarItem(
                    icon = Icons.AutoMirrored.Filled.Help,
                    label = "Dúvidas",
                    onClick = onNavigationToFaqScreen
                )

                // Perfil
//                BottomBarItem(
//                    icon = Icons.Outlined.Person,
//                    label = "Perfil",
//                    onClick = onNavigationToProfileScreen
//                )

                // Menu - Somente exibir se usuário estiver logado
                if (isLoggedIn) {
                    BottomBarItem(
                        icon = Icons.Outlined.Menu,
                        label = "Menu",
                        onClick = onNavigationToMenuScreen
                    )
                }
            }
        }
    )
}

@Composable
private fun BottomBarItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top, // 👈 não centraliza
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 2.dp) // 👈 MUITO importante
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp)) // 👈 controle fino do espaço
        Text(
            text = label,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            maxLines = 1,
            lineHeight = 11.sp, // 👈 cola no ícone
            modifier = Modifier.padding(top = 0.dp)
        )
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun BottomBarPreview() {
    XBizWorkTheme {
        AppBottomBar(
            onNavigationToProfileScreen = {},
            onNavigationToSearchScreen = {},
            onNavigationToFaqScreen = {},
            onNavigationToMenuScreen = {},
            onNavigationToSearchProfessionalSkillScreen = {}
        )
    }
}