package com.chuckerteam.chucker.internal.ui.compose.screens.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.R
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme


@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun TopBar(
    title: String,
    subtitle: String,
    encodeUrl: Boolean,
    onBack: () -> Unit = { TODO() },
    switchUrlEncoding: () -> Unit = { TODO() },
    onShareText: () -> Unit = { TODO() },
    onShareCurl: () -> Unit = { TODO() },
    onShareFile: () -> Unit = { TODO() },
    onShareHar: () -> Unit = { TODO() },
) {
    TopAppBar(
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = AppTheme.typography.titleMedium,
                    color = AppTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = AppTheme.typography.bodySmall,
                    color = AppTheme.colorScheme.secondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            IconButton(onClick = switchUrlEncoding) {
                Icon(
                    painter = painterResource(
                        id = if (encodeUrl) R.drawable.chucker_ic_encoded_url_white else R.drawable.chucker_ic_decoded_url_white
                    ),
                    contentDescription = stringResource(id = R.string.chucker_encode_url)
                )
            }
            TransactionExportDropdownMenu(
                onShareText = onShareText,
                onShareCurl = onShareCurl,
                onShareFile = onShareFile,
                onShareHar = onShareHar,
            )
        }
    )
}

@AppPreview
@Composable
private fun TopBarPreview() {
    AppTheme {
        TopBar(
            title = "https://stackoverflow.com/questions/41311344/installing-android-emulator-in-android-studio-with-zip-files",
            subtitle = "GET",
            encodeUrl = false
        )
    }
}
