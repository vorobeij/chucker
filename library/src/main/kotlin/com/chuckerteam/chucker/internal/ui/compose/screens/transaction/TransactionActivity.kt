package com.chuckerteam.chucker.internal.ui.compose.screens.transaction

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.lifecycleScope
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.core.BaseChuckerActivity
import com.chuckerteam.chucker.internal.data.cache.UserSettings
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.support.HarUtils
import com.chuckerteam.chucker.internal.support.Sharable
import com.chuckerteam.chucker.internal.support.TransactionCurlCommandSharable
import com.chuckerteam.chucker.internal.support.TransactionDetailsHarSharable
import com.chuckerteam.chucker.internal.support.TransactionDetailsSharable
import com.chuckerteam.chucker.internal.support.shareAsFile
import com.chuckerteam.chucker.internal.support.shareAsUtf8Text
import com.chuckerteam.design.system.theme.AppTheme
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
                    val coroutineScope = rememberCoroutineScope()

                    val transaction by viewModel.transaction.observeAsState()
                    val formatRequestBody by viewModel.formatRequestBody.observeAsState()
                    val encodeUrl by viewModel.encodeUrl.observeAsState(false)
                    val transactionTitle by viewModel.transactionTitle.observeAsState()

                    Scaffold(
                        topBar = {
                            TopBar(
                                title = transactionTitle.orEmpty(),
                                subtitle = transaction?.method.orEmpty(),
                                encodeUrl = encodeUrl,
                                onBack = { onBackPressedDispatcher.onBackPressed() },
                                switchUrlEncoding = { viewModel.switchUrlEncoding() },
                                onShareText = { shareAsText { TransactionDetailsSharable(it, encodeUrl) } },
                                onShareCurl = { shareAsText { TransactionCurlCommandSharable(it) } },
                                onShareFile = { shareFile(encodeUrl) },
                                onShareHar = { shareHar() }
                            )
                        }
                    ) { padding ->
                        val pagerState = rememberPagerState(initialPage = UserSettings.Transaction.openedTabIndex) { 3 }
                        val tabs = listOf(
                            stringResource(id = R.string.chucker_overview),
                            stringResource(id = R.string.chucker_request),
                            stringResource(id = R.string.chucker_response)
                        )

                        ViewPager(
                            padding = padding,
                            pagerState = pagerState,
                            tabs = tabs,
                            coroutineScope = coroutineScope,
                            encodeUrl = encodeUrl,
                            transaction = transaction ?: HttpTransaction(),
                            formatRequestBody = formatRequestBody ?: true,
                        )
                    }
                }
            }
        }
    }


    private fun shareFile(encodeUrl: Boolean) {
        shareAsFileWrapper("transaction.txt") {
            TransactionDetailsSharable(
                transaction = it,
                encodeUrls = encodeUrl
            )
        }
    }

    private fun shareHar() {
        shareAsFileWrapper("transaction.har") {
            TransactionDetailsHarSharable(
                HarUtils.harStringFromTransactions(
                    transactions = listOf(it),
                    name = getString(R.string.chucker_name),
                    version = getString(R.string.chucker_version)
                )
            )
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
