package com.br.xbizitwork.ui.presentation.components.topbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.example.xbizitwork.R
import com.br.xbizitwork.ui.presentation.common.StringAssets
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFOntFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    username: String = "João",
    onRightIconClick: () -> Unit = {},
    companyLogoContent: @Composable () -> Unit = { CompanyLogo() }
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // Left: Company logo with circular background
                Column(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                ) {
                    companyLogoContent()
                }

                // Center: Greeting + Username (vertical layout)
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = StringAssets.WELCOME_TEXT),
                        fontSize = 12.sp,
                        fontFamily = poppinsFOntFamily,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = username,
                        fontSize = 14.sp,
                        fontFamily = poppinsFOntFamily,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }

                // Right: Person icon button (configurable)
                Column(
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(id = StringAssets.SETTINGS_TEXT),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(
                                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                            .clickable(onClick = onRightIconClick)
                            .padding(4.dp)
                    )
                }
            }
        }
    )
}

@Composable
private fun CompanyLogo(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(
                color = Color(0xFF082f49),
                shape = CircleShape
            )
            .padding(4.dp)
    ) {
        Text(
            text = stringResource(id = StringAssets.APP_NAME_XBIZ),
            fontSize = 9.sp,
            fontFamily = poppinsFOntFamily,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFF6E10),
            textAlign = TextAlign.Center,
            lineHeight = 9.sp
        )
        Text(
            text = stringResource(id = StringAssets.APP_NAME_WORK),
            fontSize = 8.sp,
            fontFamily = poppinsFOntFamily,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 8.sp
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTopBarPreview() {
    XBizWorkTheme {
        AppTopBar(
            username = "João Silva",
            onRightIconClick = { },
        )
    }
}