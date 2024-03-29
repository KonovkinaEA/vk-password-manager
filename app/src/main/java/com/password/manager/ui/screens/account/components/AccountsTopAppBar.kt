package com.password.manager.ui.screens.account.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.password.manager.ui.screens.account.model.AccountUiAction
import com.password.manager.ui.theme.ExtendedTheme
import com.password.manager.ui.theme.Green
import com.password.manager.ui.theme.PasswordManagerTheme
import com.password.manager.ui.theme.ThemeModePreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsTopAppBar(enableSave: Boolean, onUiAction: (AccountUiAction) -> Unit) {
    TopAppBar(
        modifier = Modifier.shadow(10.dp),
        title = {},
        navigationIcon = {
            IconButton(onClick = { onUiAction(AccountUiAction.CloseAccount) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back to the List screen button icon",
                    tint = ExtendedTheme.colors.labelPrimary
                )
            }
        },
        actions = {
            AppBarTextButton(
                enable = enableSave,
                text = "Save",
                containerColor = Green
            ) { onUiAction(AccountUiAction.SaveAccount) }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ExtendedTheme.colors.backSecondary
        )
    )
}

@Preview
@Composable
private fun TopAppBarPreview(
    @PreviewParameter(ThemeModePreview::class) darkTheme: Boolean
) {
    PasswordManagerTheme(darkTheme = darkTheme) {
        AccountsTopAppBar(enableSave = true) {}
    }
}
