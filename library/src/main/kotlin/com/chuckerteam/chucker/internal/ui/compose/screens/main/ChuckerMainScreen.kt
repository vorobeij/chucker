package com.chuckerteam.chucker.internal.ui.compose.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.data.entity.HttpTransactionTuple
import com.chuckerteam.chucker.internal.ui.compose.views.TransactionListItem
import com.chuckerteam.design.system.components.FilterChipRow
import com.chuckerteam.design.system.components.FilterOption
import com.chuckerteam.design.system.components.icons.Filters
import com.chuckerteam.design.system.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChuckerMainScreen(
    transactions: List<HttpTransactionTuple>,
    applicationName: String,
    onTransactionClick: (Long) -> Unit,
    onClearClick: () -> Unit,
    onShareTextClick: () -> Unit,
    onShareHarClick: () -> Unit,
    onSaveTextClick: () -> Unit,
    onSaveHarClick: () -> Unit,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(
        contentWindowInsets = WindowInsets.statusBars,
        topBar = {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (isSearchActive) {
                    MySearchBar(
                        onQueryChange = onQueryChange,
                        onSearch = {},
                        modifier = modifier.fillMaxWidth()
                    )
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
                            IconButton(onClick = { isSearchActive = !isSearchActive }) {
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
                        modifier = modifier,
                        windowInsets = WindowInsets(0.dp)
                    )
                }

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
                FilterChipRow(
                    options = filterOptions,
                    selectedIds = selectedFilters,
                    onSelectionChange = { selectedFilters = it },
                )
            }
        }
    ) { padding ->
        if (transactions.isEmpty()) {
            ChuckerTutorialSection(modifier = Modifier.padding(padding))
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(transactions, key = { it.id }) { transaction ->
                    TransactionListItem(
                        transaction = transaction,
                        onClick = onTransactionClick
                    )
                }
            }
        }
    }
}
