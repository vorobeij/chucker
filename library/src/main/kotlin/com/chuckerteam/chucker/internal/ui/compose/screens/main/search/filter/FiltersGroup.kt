package com.chuckerteam.chucker.internal.ui.compose.screens.main.search.filter

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.internal.data.repository.search.HttpMethod
import com.chuckerteam.chucker.internal.data.repository.search.SearchFilter
import com.chuckerteam.chucker.internal.data.repository.search.SearchIn
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun FiltersGroup(
    modifier: Modifier = Modifier,
//    searchFilter: SearchFilter
) {

    var searchFilter: SearchFilter by remember { mutableStateOf(SearchFilter()) }

    // Single select
    Column {
        GroupLabel("Type")
        FiltersFlowRow {
            HttpMethod.entries.forEach { httpMethod ->
                FilterChip(
                    label = httpMethod.name,
                    selected = searchFilter.method == httpMethod
                ) { isSelected ->
                    searchFilter = searchFilter.copy(method = if (isSelected) httpMethod else null)
                }
            }
        }
    }

    // Multiple selection
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GroupLabel("Search in…")
            Text(
                text = "Clear",
                style = AppTheme.typography.bodySmall,
                color = AppTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clickable { searchFilter = searchFilter.copy(searchIn = emptySet()) },
            )
        }
        FiltersFlowRow {
            SearchIn.entries.forEach { searchIn ->
                FilterChip(label = searchIn.name, selected = searchIn in searchFilter.searchIn) { isSelected ->
                    searchFilter = searchFilter.copy(
                        searchIn = if (isSelected) {
                            searchFilter.searchIn + searchIn
                        } else {
                            searchFilter.searchIn - searchIn
                        }
                    )
                }
            }
        }

    }
}

@Composable
private fun FiltersFlowRow(
    modifier: Modifier = Modifier,
    content: @Composable FlowRowScope.() -> Unit,
) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        itemVerticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        content = content
    )
}

@Composable
private fun GroupLabel(text: String) {
    Text(
        text = text,
        style = AppTheme.typography.titleSmall,
        color = AppTheme.colorScheme.onSurface,
        modifier = Modifier.padding(horizontal = 16.dp),
    )
}

@Composable
private fun FilterChip(
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

@AppPreview
@Composable
private fun Preview() {
    AppTheme {
        Column() {
            FiltersGroup()
        }
    }
}
