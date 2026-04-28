package com.chuckerteam.design.system.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chuckerteam.design.system.theme.AppTheme

@Composable
public fun LabelButton(
    text: String,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        style = AppTheme.typography.bodyMedium,
        color = AppTheme.colorScheme.primary,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        modifier = Modifier
            .clickable { TODO() }
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
