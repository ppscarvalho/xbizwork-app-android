package com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.state.SearchProfessionalBySkillUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Content composable for professional search by skill
 * Following the same pattern as SkillsContent
 */
@Composable
fun SearchProfessionalBySkillContent(
    modifier: Modifier = Modifier,
    uiState: SearchProfessionalBySkillUIState,
    paddingValues: PaddingValues,
    onEvent: (SearchProfessionalBySkillEvent) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    AppGradientBackground(
        modifier = modifier,
        paddingValues = paddingValues
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search instruction
            Text(
                text = "Busque profissionais por habilidade",
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )

            // Search field with button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = { 
                        onEvent(SearchProfessionalBySkillEvent.OnSearchQueryChanged(it)) 
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp),
                    placeholder = { 
                        Text(
                            text = "Ex: pedreiro, eletricista...",
                            fontFamily = poppinsFontFamily
                        ) 
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { 
                                    onEvent(SearchProfessionalBySkillEvent.OnClearSearch) 
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search"
                                )
                            }
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            keyboardController?.hide()
                            onEvent(SearchProfessionalBySkillEvent.OnSearchClicked)
                        }
                    ),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )

                AppButton(
                    modifier = Modifier.height(56.dp),
                    text = "",
                    icon = Icons.Default.Search,
                    enabled = uiState.searchQuery.isNotEmpty() && !uiState.isLoading,
                    isLoading = uiState.isLoading,
                    contentColor = Color.White,
                    onClick = { 
                        keyboardController?.hide()
                        onEvent(SearchProfessionalBySkillEvent.OnSearchClicked) 
                    }
                )
            }

            // Results container
            SearchProfessionalBySkillContainer(
                modifier = Modifier.fillMaxSize(),
                uiState = uiState
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0f344e
)
@Composable
private fun SearchProfessionalBySkillContentPreview() {
    XBizWorkTheme {
        SearchProfessionalBySkillContent(
            uiState = SearchProfessionalBySkillUIState(
                searchQuery = "educador"
            ),
            paddingValues = PaddingValues(0.dp),
            onEvent = {}
        )
    }
}
