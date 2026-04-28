package com.chuckerteam.chucker.internal.ui.compose.screens.main.search.filter

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun GroupLabel(text: String) {
    Text(
        text = text,
        style = AppTheme.typography.titleSmall,
        color = AppTheme.colorScheme.onSurface,
        modifier = Modifier.padding(horizontal = 16.dp),
    )
}
