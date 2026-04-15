package com.chuckerteam.chucker.internal.ui.compose.screens.transaction

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.support.HarUtils
import com.chuckerteam.chucker.internal.support.Sharable
import com.chuckerteam.chucker.internal.support.TransactionCurlCommandSharable
import com.chuckerteam.chucker.internal.support.TransactionDetailsHarSharable
import com.chuckerteam.chucker.internal.support.TransactionDetailsSharable
import com.chuckerteam.chucker.internal.support.shareAsFile
import com.chuckerteam.chucker.internal.support.shareAsUtf8Text
import com.chuckerteam.chucker.internal.core.BaseChuckerActivity
import com.chuckerteam.chucker.internal.ui.compose.screens.overview.TransactionOverviewScreen
import com.chuckerteam.chucker.internal.ui.compose.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class TransactionActivity : BaseChuckerActivity() {
    private val viewModel: TransactionViewModel by viewModels {
        TransactionViewModelFactory(intent.getLongExtra(EXTRA_TRANSACTION_ID, 0))
    }

    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var encodeUrl by remember { mutableStateOf(false) }
                    var transactionTitle by remember { mutableStateOf("") }
                    val coroutineScope = rememberCoroutineScope()

                    LaunchedEffect(viewModel) {
                        viewModel.transactionTitle.observe(this@TransactionActivity) { transactionTitle = it ?: "" }
                        viewModel.encodeUrl.observe(this@TransactionActivity) { encodeUrl = it }
                    }

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text(text = transactionTitle) },
                                navigationIcon = {
                                    IconButton(onClick = { onBackPressedDispatcher.onBackPressed() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = null
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = { viewModel.switchUrlEncoding() }) {
                                        Icon(
                                            painter = painterResource(
                                                id = if (encodeUrl) R.drawable.chucker_ic_encoded_url_white else R.drawable.chucker_ic_decoded_url_white
                                            ),
                                            contentDescription = stringResource(id = R.string.chucker_encode_url)
                                        )
                                    }
                                    TransactionExportDropdownMenu(
                                        onShareText = { shareAsText { TransactionDetailsSharable(it, encodeUrl) } },
                                        onShareCurl = { shareAsText { TransactionCurlCommandSharable(it) } },
                                        onShareFile = {
                                            shareAsFileWrapper("transaction.txt") {
                                                TransactionDetailsSharable(
                                                    it,
                                                    encodeUrl
                                                )
                                            }
                                        },
                                        onShareHar = {
                                            shareAsFileWrapper("transaction.har") {
                                                TransactionDetailsHarSharable(
                                                    HarUtils.harStringFromTransactions(
                                                        listOf(it),
                                                        getString(R.string.chucker_name),
                                                        getString(R.string.chucker_version)
                                                    )
                                                )
                                            }
                                        }
                                    )
                                }
                            )
                        }
                    ) { padding ->
                        val pagerState = rememberPagerState(initialPage = 0) { 3 }
                        val tabs = listOf(
                            stringResource(id = R.string.chucker_overview),
                            stringResource(id = R.string.chucker_request),
                            stringResource(id = R.string.chucker_response)
                        )

                        Column(modifier = Modifier.padding(padding)) {
                            TabRow(selectedTabIndex = pagerState.currentPage) {
                                tabs.forEachIndexed { index, title ->
                                    Tab(
                                        selected = pagerState.currentPage == index,
                                        onClick = { coroutineScope.launch { pagerState.scrollToPage(index) } },
                                        text = { Text(title) }
                                    )
                                }
                            }
                            HorizontalPager(state = pagerState) { page ->
                                when (page) {
                                    0 -> TransactionOverviewScreen(
                                        transaction = viewModel.transaction.value ?: HttpTransaction(),
                                        encodeUrl = encodeUrl,
                                        modifier = Modifier.fillMaxSize()
                                    )

                                    1 -> TransactionPayloadScreen(
                                        payloadType = PayloadType.REQUEST,
                                        transaction = viewModel.transaction.value,
                                        formatRequestBody = viewModel.formatRequestBody.value ?: true,
                                        onSaveToFile = {},
                                        modifier = Modifier.fillMaxSize()
                                    )

                                    2 -> TransactionPayloadScreen(
                                        payloadType = PayloadType.RESPONSE,
                                        transaction = viewModel.transaction.value,
                                        formatRequestBody = true,
                                        onSaveToFile = {},
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun shareAsText(block: (HttpTransaction) -> Sharable) {
        val transaction = viewModel.transaction.value ?: run {
            Toast.makeText(this, R.string.chucker_request_not_ready, Toast.LENGTH_SHORT).show()
            return
        }
        val sharable = block(transaction)
        lifecycleScope.launch {
            val intent = sharable.shareAsUtf8Text(
                activity = this@TransactionActivity,
                intentTitle = getString(R.string.chucker_share_transaction_title),
                intentSubject = getString(R.string.chucker_share_transaction_subject)
            )
            startActivity(intent)
        }
    }

    private fun shareAsFileWrapper(fileName: String, block: suspend (HttpTransaction) -> Sharable) {
        lifecycleScope.launch {
            val transaction = viewModel.transaction.value ?: run {
                Toast.makeText(this@TransactionActivity, R.string.chucker_request_not_ready, Toast.LENGTH_SHORT).show()
                return@launch
            }
            val sharable = block(transaction)
            val intent = withContext(Dispatchers.IO) {
                sharable.shareAsFile(
                    activity = this@TransactionActivity,
                    fileName = fileName,
                    intentTitle = getString(R.string.chucker_share_transaction_title),
                    intentSubject = getString(R.string.chucker_share_transaction_subject),
                    clipDataLabel = "transaction"
                )
            }
            if (intent != null) startActivity(intent)
            else Toast.makeText(applicationContext, R.string.chucker_export_no_file, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val EXTRA_TRANSACTION_ID = "transaction_id"
        fun start(context: Context, transactionId: Long) {
            context.startActivity(Intent(context, TransactionActivity::class.java).apply {
                putExtra(EXTRA_TRANSACTION_ID, transactionId)
            })
        }
    }
}

@Composable
private fun TransactionExportDropdownMenu(
    onShareText: () -> Unit,
    onShareCurl: () -> Unit,
    onShareFile: () -> Unit,
    onShareHar: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = true }) {
        Icon(Icons.Default.Share, contentDescription = stringResource(id = R.string.chucker_export))
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
        DropdownMenuItem(text = { Text(stringResource(id = R.string.chucker_share_as_text)) }, onClick = { expanded = false; onShareText() })
        DropdownMenuItem(text = { Text(stringResource(id = R.string.chucker_share_as_curl)) }, onClick = { expanded = false; onShareCurl() })
        DropdownMenuItem(text = { Text(stringResource(id = R.string.chucker_share_as_file)) }, onClick = { expanded = false; onShareFile() })
        DropdownMenuItem(text = { Text(stringResource(id = R.string.chucker_share_as_har)) }, onClick = { expanded = false; onShareHar() })
    }
}
