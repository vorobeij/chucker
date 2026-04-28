package com.chuckerteam.chucker.internal.ui.compose.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.internal.data.entity.HttpTransactionTuple
import com.chuckerteam.chucker.internal.data.repository.search.SearchFilter
import com.chuckerteam.chucker.internal.ui.compose.screens.main.views.MyAppBar
import com.chuckerteam.chucker.internal.ui.compose.views.TransactionListItem

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
    onQueryChange: (SearchFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        MyAppBar(
            onSearch = onQueryChange,
            modifier = modifier.fillMaxWidth()
        )
        if (transactions.isEmpty()) {
            ChuckerTutorialSection(modifier = Modifier)
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
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
