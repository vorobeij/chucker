package com.chuckerteam.chucker.internal.ui.compose.screens.main.search.filter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chuckerteam.chucker.internal.data.repository.search.SearchFilter
import com.chuckerteam.chucker.internal.data.repository.search.SearchIn
import com.chuckerteam.design.system.components.LabelButton
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun SearchInFilterGroup(
    searchFilter: SearchFilter,
    onSearchFilterChanged: (SearchFilter) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GroupLabel("Search in…")
            LabelButton("Clear") { onSearchFilterChanged(searchFilter.copy(searchIn = emptySet())) }
        }
        FiltersFlowRow {
            SearchIn.entries.forEach { searchIn ->
                FilterChip(label = searchIn.name, selected = searchIn in searchFilter.searchIn) { isSelected ->
                    onSearchFilterChanged(
                        searchFilter.copy(
                            searchIn = if (isSelected) {
                                searchFilter.searchIn + searchIn
                            } else {
                                searchFilter.searchIn - searchIn
                            }
                        )
                    )
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
            SearchInFilterGroup(
                searchFilter = SearchFilter()
            ) {}
        }
    }
}
