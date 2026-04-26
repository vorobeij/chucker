package com.chuckerteam.chucker.internal.ui.compose.screens.main.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chuckerteam.design.system.components.SwipeToDismissLayout
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Suggestion(
    text: String,
    modifier: Modifier = Modifier,
    onClick: (s: String) -> Unit = {},
    onDelete: () -> Unit = {}
) {
    SwipeToDismissLayout(
        modifier = modifier,
        onDelete = onDelete
    ) {
        Text(
            text = text,
            style = AppTheme.typography.bodyMedium,
            color = AppTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .clickable { onClick(text) }
                .background(AppTheme.colorScheme.surfaceBright)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

@AppPreview
@Composable
private fun SuggestionPreview() {
    AppTheme {
        Suggestion(
            text = "test string"
        )
    }
}
