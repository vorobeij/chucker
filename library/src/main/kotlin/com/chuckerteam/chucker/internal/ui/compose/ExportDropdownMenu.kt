package com.chuckerteam.chucker.internal.ui.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.ui.compose.icons.FileDownload

@Composable
internal fun ExportDropdownMenu(
    onClearClick: () -> Unit,
    onShareTextClick: () -> Unit,
    onShareHarClick: () -> Unit,
    onSaveTextClick: () -> Unit,
    onSaveHarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.Share, contentDescription = stringResource(R.string.chucker_export))
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = modifier
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.chucker_clear)) },
            onClick = { expanded = false; onClearClick() },
            leadingIcon = { Icon(Icons.Default.Delete, contentDescription = null) }
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.chucker_share_as_text)) },
            onClick = { expanded = false; onShareTextClick() },
            leadingIcon = { Icon(Icons.Default.Share, contentDescription = null) }
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.chucker_share_as_har)) },
            onClick = { expanded = false; onShareHarClick() },
            leadingIcon = { Icon(Icons.Default.Share, contentDescription = null) }
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.chucker_save_as_text)) },
            onClick = { expanded = false; onSaveTextClick() },
            leadingIcon = { Icon(Icons.Default.FileDownload, contentDescription = null) }
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.chucker_save_as_har)) },
            onClick = { expanded = false; onSaveHarClick() },
            leadingIcon = { Icon(Icons.Default.FileDownload, contentDescription = null) }
        )
    }
}
