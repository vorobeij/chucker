package com.chuckerteam.chucker.internal.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.ui.compose.theme.AppTheme

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
    modifier: Modifier = Modifier
) {

    if (isSearchActive) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { query -> onQueryChange(query) },
            onSearch = {},
            active = true,
            onActiveChange = { TODO()/*isSearchActive = !it*/ },
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
            },
            modifier = modifier.fillMaxWidth()
        ) {}
    } else {
        TopAppBar(
            title = {
                Column {
                    Text(stringResource(R.string.chucker_network_tutorial))
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
