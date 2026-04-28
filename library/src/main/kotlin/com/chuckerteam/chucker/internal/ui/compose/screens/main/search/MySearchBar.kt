package com.chuckerteam.chucker.internal.ui.compose.screens.main.search

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.data.entity.SuggestionEntity
import com.chuckerteam.chucker.internal.data.repository.search.SearchFilter
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MySearchBar(
    onSearch: (SearchFilter) -> Unit
) {
    val viewModel: SearchBarViewModel = viewModel()
    val suggestions by viewModel.suggestions.observeAsState(initial = emptyList())
    var searchFilter by remember { mutableStateOf(SearchFilter()) } // todo load from viewmode

    MySearchBarComponent(
        suggestions = suggestions,
        onSearch = {
            viewModel.onSearch(searchFilter.query)
            onSearch(searchFilter)
        },
        onQueryChange = viewModel::onQueryChanged,
        onDelete = viewModel::onDelete,
        searchFilter = searchFilter,
        onFilterChanged = { searchFilter = it }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MySearchBarComponent(
    modifier: Modifier = Modifier,
    isSearchActive: Boolean = false,
    suggestions: List<SuggestionEntity>,
    searchFilter: SearchFilter,
    onQueryChange: (String) -> Unit = {},
    onFilterChanged: (SearchFilter) -> Unit = {},
    onSearch: () -> Unit,
    onDelete: (String) -> Unit = {},
) {

    var isSearchActive by remember { mutableStateOf(isSearchActive) }

    var searchQuery by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isSearchActive) {
        if (isSearchActive) {
            focusRequester.requestFocus()
        }
    }

    val clearFocusAndSearch: (String) -> Unit = remember {
        { s: String ->
            focusManager.clearFocus()
            searchQuery = s
            isSearchActive = false
            onSearch()
        }
    }

    SearchBar(
        windowInsets = WindowInsets(0, 0, 0, 0),
        inputField = {
            SearchBarDefaults.InputField(
                query = searchQuery,
                onQueryChange = {
                    searchQuery = it
                    onQueryChange(it)
                },
                onSearch = { clearFocusAndSearch(it) },
                expanded = isSearchActive,
                onExpandedChange = { isSearchActive = it },
                placeholder = { Text(stringResource(R.string.chucker_search)) },
                leadingIcon = {
                    IconButton(onClick = { isSearchActive = true }) {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { clearFocusAndSearch("") }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier
                    .focusRequester(focusRequester)
            )
        },
        expanded = isSearchActive,
        onExpandedChange = { isSearchActive = it },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = if (isSearchActive) 0.dp else 16.dp)
    ) {
        SearchFilters(
            modifier = Modifier,
            suggestions = suggestions,
            searchFilter = searchFilter,
            clearFocusAndSearch = clearFocusAndSearch,
            onDelete = onDelete,
            onSearchFilterChanged = onFilterChanged
        )
    }
}

@AppPreview
@Composable
private fun Preview() {
    AppTheme {
        MySearchBarComponent(
            isSearchActive = false,
            suggestions = emptyList(),
            searchFilter = SearchFilter(),
            onFilterChanged = {},
            onSearch = {},
        )
    }
}

@AppPreview
@Composable
private fun Preview2() {
    AppTheme {
        MySearchBarComponent(
            isSearchActive = true,
            suggestions = suggestionsMock,
            searchFilter = SearchFilter(),
            onFilterChanged = {},
            onSearch = {},
        )
    }
}
