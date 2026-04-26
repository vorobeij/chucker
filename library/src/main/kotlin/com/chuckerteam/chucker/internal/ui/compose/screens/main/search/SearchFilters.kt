package com.chuckerteam.chucker.internal.ui.compose.screens.main.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.internal.data.entity.SuggestionEntity
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
private fun SearchHistoryButton(
    text: String,
    onClick: () -> Unit
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

@Composable
internal fun SearchFilters(
    modifier: Modifier = Modifier,
    suggestions: List<SuggestionEntity>,
    clearFocusAndSearch: (String) -> Unit = {},
    onDelete: (String) -> Unit = {}, // todo use id
) {
    /*
[Clear history]                                            [All]
[history search 1 x], [history search 2 x], [history search 3 x]

Search in
[URL] [Req body] [Response body] - see all the params of Translation

Type
[HTTP] [GQL] [SOCKETS]

Method
[GET] [POST] [SEND] [RECEIVE]

Teams
[instrument] [market]

*Teams - first parts of url: <host>/part1/part2?qparam1=xxx&qparam2=yyy
         */
    Column {
        var historyLines by remember { mutableIntStateOf(2) }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SearchHistoryButton(
                text = "Clear",
                onClick = { TODO() }
            )
            SearchHistoryButton(
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
            suggestions = suggestionsMock
        )
    }
}
