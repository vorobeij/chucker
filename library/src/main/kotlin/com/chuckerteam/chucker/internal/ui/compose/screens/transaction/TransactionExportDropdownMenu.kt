package com.chuckerteam.chucker.internal.ui.compose.screens.transaction

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.chuckerteam.chucker.R
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun TransactionExportDropdownMenu(
    onShareText: () -> Unit = {},
    onShareCurl: () -> Unit = {},
    onShareFile: () -> Unit = {},
    onShareHar: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(
            imageVector = Icons.Default.Share,
            contentDescription = stringResource(id = R.string.chucker_export)
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.chucker_share_as_text)) },
            onClick = { expanded = false; onShareText() })
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.chucker_share_as_curl)) },
            onClick = { expanded = false; onShareCurl() })
        DropdownMenuItem(
            text = { Text(stringResource(id = R.string.chucker_share_as_file)) },
            onClick = { expanded = false; onShareFile() }
        )
        DropdownMenuItem(
            text = {
                Text(stringResource(id = R.string.chucker_share_as_har))
            },
            onClick = { expanded = false; onShareHar() }
        )
    }
}

@AppPreview
@Composable
private fun TopBarPreview() {
    AppTheme {
        TransactionExportDropdownMenu()
    }
}
