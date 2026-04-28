package com.chuckerteam.chucker.internal.ui.compose.screens.main.search.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.chuckerteam.chucker.internal.data.repository.search.HttpMethod
import com.chuckerteam.chucker.internal.data.repository.search.SearchFilter
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun HttpMethodFilterGroup(
    searchFilter: SearchFilter,
    onSearchFilterChanged: (SearchFilter) -> Unit
) {
    Column {
        GroupLabel("Type")
        FiltersFlowRow {
            HttpMethod.entries.forEach { httpMethod ->
                FilterChip(
                    label = httpMethod.name,
                    selected = searchFilter.method == httpMethod
                ) { isSelected ->
                    onSearchFilterChanged(searchFilter.copy(method = if (isSelected) httpMethod else null))
                }
            }
        }
    }
}

@AppPreview
@Composable
private fun Preview() {
    AppTheme {
        Column {
            HttpMethodFilterGroup(
                searchFilter = SearchFilter(),
                onSearchFilterChanged = {}
            )
        }
    }
}
