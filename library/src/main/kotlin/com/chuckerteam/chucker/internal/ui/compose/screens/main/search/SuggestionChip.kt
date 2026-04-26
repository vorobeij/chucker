package com.chuckerteam.chucker.internal.ui.compose.screens.main.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SuggestionChip(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (s: String) -> Unit = {},
    onDelete: (text: String) -> Unit = {}
) {
    InputChip(
        selected = false,
        onClick = { onClick(text) },
        label = {
            Text(
                text = text,
                style = AppTheme.typography.labelMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier
                    .clickable { onClick(text) }
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .padding(4.dp)
                    .clickable { onDelete(text) }
            )
        },
        modifier = modifier
    )
}

@AppPreview
@Composable
private fun Preview() {
    AppTheme {
        SuggestionChip(
            text = "post"
        )
    }
}
