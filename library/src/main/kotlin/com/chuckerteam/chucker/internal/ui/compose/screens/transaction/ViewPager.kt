package com.chuckerteam.chucker.internal.ui.compose.screens.transaction

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chuckerteam.chucker.internal.data.cache.UserSettings
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.ui.compose.screens.overview.TransactionOverviewScreen
import com.chuckerteam.chucker.internal.ui.compose.screens.payload.PayloadType
import com.chuckerteam.chucker.internal.ui.compose.screens.payload.TransactionPayloadScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalFoundationApi::class)
internal fun ViewPager(
    padding: PaddingValues,
    pagerState: PagerState,
    tabs: List<String>,
    coroutineScope: CoroutineScope,
    encodeUrl: Boolean,
    transaction: HttpTransaction,
    formatRequestBody: Boolean
) {
    Column(modifier = Modifier.padding(padding)) {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            UserSettings.Transaction.openedTabIndex = index
                            pagerState.scrollToPage(index)
                        }
                    },
                    text = { Text(title) }
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> TransactionOverviewScreen(
                    transaction = transaction,
                    encodeUrl = encodeUrl,
                    modifier = Modifier.fillMaxSize()
                )

                1 -> TransactionPayloadScreen(
                    payloadType = PayloadType.REQUEST,
                    transaction = transaction,
                    formatRequestBody = formatRequestBody,
                    onSaveToFile = {},
                    modifier = Modifier.fillMaxSize()
                )

                2 -> TransactionPayloadScreen(
                    payloadType = PayloadType.RESPONSE,
                    transaction = transaction,
                    formatRequestBody = true,
                    onSaveToFile = {},
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
