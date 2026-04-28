package com.chuckerteam.chucker.internal.ui.compose.screens.main.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.internal.data.entity.SuggestionEntity
import com.chuckerteam.chucker.internal.data.repository.search.HttpMethod
import com.chuckerteam.chucker.internal.data.repository.search.SearchFilter
import com.chuckerteam.chucker.internal.data.repository.search.SearchIn
import com.chuckerteam.chucker.internal.ui.compose.screens.main.search.filter.HttpMethodFilterGroup
import com.chuckerteam.chucker.internal.ui.compose.screens.main.search.filter.SearchInFilterGroup
import com.chuckerteam.design.system.components.LabelButton
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun SearchFilters(
    modifier: Modifier = Modifier,
    suggestions: List<SuggestionEntity>,
    searchFilter: SearchFilter,
    onSearchFilterChanged: (SearchFilter) -> Unit,
    clearFocusAndSearch: (String) -> Unit = {},
    onDelete: (String) -> Unit = {}, // todo use id
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column {
            var historyLines by remember { mutableIntStateOf(2) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LabelButton(
                    text = "Clear",
                    onClick = { TODO() }
                )
                LabelButton(
                    text = "All",
                    onClick = { historyLines = Int.MAX_VALUE }
                )
            }
            FlowRow(
                modifier = Modifier.padding(horizontal = 16.dp),
                itemVerticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                maxLines = historyLines
            ) {
                suggestions.forEach { suggestionEntity ->
                    SuggestionChip(
                        text = suggestionEntity.query,
                        onClick = { s -> clearFocusAndSearch(s) },
                        onDelete = { onDelete(suggestionEntity.query) },// todo by id
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
        }

        // Single select
        HttpMethodFilterGroup(searchFilter, onSearchFilterChanged)

        // Multiple selection
        SearchInFilterGroup(searchFilter, onSearchFilterChanged)
    }
}

internal val suggestionsMock = listOf(
    SuggestionEntity(id = 0, query = "Morbi vestibulum orci"),
    SuggestionEntity(id = 1, query = "Donec sit amet diam"),
    SuggestionEntity(id = 2, query = "Maecenas efficitur neque"),
    SuggestionEntity(id = 3, query = "Ut maximus orci non"),
)

@AppPreview
@Composable
private fun Preview() {
    AppTheme {
        SearchFilters(
            suggestions = suggestionsMock,
            searchFilter = SearchFilter(
                query = "test",
                method = HttpMethod.GET,
                searchIn = setOf(SearchIn.REQUEST_BODY, SearchIn.RESPONSE_BODY)
            ),
            onSearchFilterChanged = {}
        )
    }
}
