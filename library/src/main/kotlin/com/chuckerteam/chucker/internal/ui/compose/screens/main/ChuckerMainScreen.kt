package com.chuckerteam.chucker.internal.ui.compose.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.internal.data.entity.HttpTransactionTuple
import com.chuckerteam.chucker.internal.ui.compose.views.TransactionListItem

@Composable
internal fun ChuckerMainScreen(
    viewModel: MainViewModel,
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
    val transactions: List<HttpTransactionTuple> by viewModel.transactions.observeAsState(emptyList())
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ChuckerTopAppBar(
                applicationName = applicationName,
                isSearchActive = isSearchActive,
                searchQuery = searchQuery,
                onSearchToggle = { isSearchActive = !isSearchActive },
                onQueryChange = { query ->
                    searchQuery = query
                    onQueryChange(query)
                },
                onClearClick = onClearClick,
                onShareTextClick = onShareTextClick,
                onShareHarClick = onShareHarClick,
                onSaveTextClick = onSaveTextClick,
                onSaveHarClick = onSaveHarClick,
                onActiveChange = { isSearchActive = !isSearchActive }
            )
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
