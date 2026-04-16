package com.chuckerteam.chucker.internal.ui.compose.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.chuckerteam.chucker.R
import com.chuckerteam.design.system.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChuckerTopAppBar(
    applicationName: String,
    isSearchActive: Boolean,
    searchQuery: String,
    onSearchToggle: () -> Unit,
    onQueryChange: (String) -> Unit,
    onClearClick: () -> Unit,
    onShareTextClick: () -> Unit,
    onShareHarClick: () -> Unit,
    onSaveTextClick: () -> Unit,
    onSaveHarClick: () -> Unit,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    if (isSearchActive) {
        SearchBar(
            inputField = {
                SearchBarDefaults.InputField(
                    query = searchQuery,
                    onQueryChange = onQueryChange,
                    onSearch = {},
                    expanded = true,
                    onExpandedChange = onActiveChange,
                    placeholder = { Text(stringResource(R.string.chucker_search)) },
                    leadingIcon = {
                        IconButton(onClick = onSearchToggle) {
                            Icon(Icons.Default.Search, contentDescription = null)
                        }
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { onQueryChange("") }) {
                                Icon(Icons.Default.Clear, contentDescription = null)
                            }
                        }
                    }
                )
            },
            expanded = true,
            onExpandedChange = onActiveChange,
            modifier = modifier.fillMaxWidth()
        ) {
            // Your dropdown/content goes here
            Text(stringResource(R.string.chucker_name))
        }
    } else {
        TopAppBar(
            title = {
                Column {
                    Text(stringResource(R.string.chucker_name))
                    Text(
                        text = applicationName,
                        style = AppTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            actions = {
                IconButton(onClick = onSearchToggle) {
                    Icon(Icons.Default.Search, contentDescription = stringResource(R.string.chucker_search))
                }
                ExportDropdownMenu(
                    onClearClick = onClearClick,
                    onShareTextClick = onShareTextClick,
                    onShareHarClick = onShareHarClick,
                    onSaveTextClick = onSaveTextClick,
                    onSaveHarClick = onSaveHarClick
                )
            },
            modifier = modifier
        )
    }
}
