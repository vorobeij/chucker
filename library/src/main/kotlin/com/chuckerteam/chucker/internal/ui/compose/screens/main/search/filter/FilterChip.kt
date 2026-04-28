package com.chuckerteam.chucker.internal.ui.compose.screens.main.search.filter

import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
internal fun FilterChip(
    label: String,
    selected: Boolean,
    onSelect: (selected: Boolean) -> Unit
) {

    FilterChip(
        selected = selected,
        onClick = {
            onSelect(!selected)
        },
        label = { Text(label) },
        leadingIcon = {},
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}
