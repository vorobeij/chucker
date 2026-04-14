package com.chuckerteam.chucker.internal.ui.compose

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.internal.data.entity.HttpTransactionTuple
import com.chuckerteam.chucker.internal.ui.MainViewModel

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
                onSaveHarClick = onSaveHarClick
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
                // ✅ HttpTransactionTuple is a single object, not a Pair
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
