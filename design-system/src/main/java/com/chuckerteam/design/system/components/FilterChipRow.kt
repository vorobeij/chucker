@file:OptIn(ExperimentalMaterial3Api::class)

package com.chuckerteam.design.system.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuckerteam.design.system.components.icons.Filters
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

public data class FilterOption(
    val id: String,
    val label: String? = null,
    val icon: @Composable (() -> Unit)? = null,
    val type: ChipType = ChipType.FILTER
)

public enum class ChipType {
    FILTER,
    ASSIST
}

@Composable
public fun FilterChipRow(
    options: List<FilterOption>,
    selectedIds: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    onAssistClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    singleSelect: Boolean = false,
    contentPadding: PaddingValues = PaddingValues(horizontal = 0.dp),
    chipSpacing: Int = 8
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(chipSpacing.dp)
    ) {
        items(options, key = { it.id }) { option ->
            val selected = selectedIds.contains(option.id)

            when (option.type) {
                ChipType.FILTER -> FilterChip(
                    selected = selected,
                    onClick = {
                        val newSelection = if (singleSelect) {
                            if (selected) emptySet() else setOf(option.id)
                        } else {
                            if (selected) {
                                selectedIds - option.id
                            } else {
                                selectedIds + option.id
                            }
                        }
                        onSelectionChange(newSelection)
                    },
                    label = { option.label?.let { Text(option.label) } },
                    leadingIcon = option.icon?.let { icon ->
                        {
                            icon()
                        }
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                ChipType.ASSIST -> AssistChip(
                    onClick = {
                        onAssistClick(option.id)
                    },
                    label = { option.label?.let { Text(option.label) } },
                    leadingIcon = option.icon?.let { icon ->
                        {
                            icon()
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun FilterExampleScreen() {
    val filterOptions = remember {
        listOf(
            FilterOption("settings", "", icon = {
                Icon(
                    imageVector = Icons.Default.Filters,
                    contentDescription = ""
                )
            }),
            FilterOption("all", "All"),
            FilterOption("network", "Network", icon = { /* Icon here */ }),
            FilterOption("database", "Database"),
            FilterOption("ui", "UI"),
            FilterOption("performance", "Performance"),
            FilterOption("security", "Security"),
            FilterOption("other", "Other")
        )
    }

    var selectedFilters by remember { mutableStateOf(setOf<String>("all")) }

    val filteredResults = remember(selectedFilters) {
        if (selectedFilters.contains("all") || selectedFilters.isEmpty()) {
            "Showing all items"
        } else {
            "Filtered by: ${selectedFilters.joinToString()}"
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Filter Logs",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        FilterChipRow(
            options = filterOptions,
            selectedIds = selectedFilters,
            onSelectionChange = { selectedFilters = it },
            singleSelect = true  // Change to false for multi-select
        )

        Text(
            text = filteredResults,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@AppPreview
@Composable
private fun Preview() {
    AppTheme {
        FilterExampleScreen()
    }
}

