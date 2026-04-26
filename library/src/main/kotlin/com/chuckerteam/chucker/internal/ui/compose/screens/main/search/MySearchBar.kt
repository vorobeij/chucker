package com.chuckerteam.chucker.internal.ui.compose.screens.main.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MySearchBar(
    onSearch: (String) -> Unit = {}
) {
    val viewModel: SearchBarViewModel = viewModel()
    val suggestions by viewModel.suggestions.collectAsState(initial = emptyList())

    MySearchBarComponent(
        suggestions = suggestions,
        onSearch = { query ->
            viewModel.onSearch(query)
            onSearch(query)
        },
        onQueryChange = viewModel::onQueryChanged
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MySearchBarComponent(
    modifier: Modifier = Modifier,
    isSearchActive: Boolean = false,
    suggestions: List<SuggestionEntity>,
    onQueryChange: (String) -> Unit = {},
    onSearch: (String) -> Unit = {}
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

    val clearFocusAndSearch = remember {
        { s: String ->
            focusManager.clearFocus()
            searchQuery = s
            isSearchActive = false
            onSearch(s)
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
        LazyColumn(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            items(
                count = suggestions.size,
                key = { suggestions[it].id },
                itemContent = {
                    Suggestion(
                        text = suggestions[it].query,
                        onClick = { s -> clearFocusAndSearch(s) }
                    )
                }
            )
        }
    }
}

@Composable
internal fun Suggestion(
    text: String,
    onClick: (s: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = AppTheme.typography.bodyMedium,
        modifier = modifier
            .clickable { onClick(text) }
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    )
}

@AppPreview
@Composable
private fun Preview() {
    AppTheme {
        MySearchBarComponent(
            isSearchActive = false,
            suggestions = emptyList()
        )
    }
}

@AppPreview
@Composable
private fun Preview2() {
    AppTheme {
        MySearchBarComponent(
            isSearchActive = true,
            suggestions = emptyList()
        )
    }
}
