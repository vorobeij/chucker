package com.chuckerteam.chucker.internal.ui.compose.screens.main

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.support.HarUtils
import com.chuckerteam.chucker.internal.support.TransactionDetailsHarSharable
import com.chuckerteam.chucker.internal.support.TransactionListDetailsSharable

@Composable
internal fun ChuckerScreenRoot(
    applicationName: String,
    onTransactionClick: (Long) -> Unit
) {
    val viewModel: MainViewModel = viewModel()
    val context = LocalContext.current

    val transactions by viewModel.transactions.observeAsState(initial = emptyList())

    var dialogConfig by remember { mutableStateOf<DialogConfig?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (!granted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Toast.makeText(
                context,
                R.string.chucker_notifications_permission_not_granted,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    val saveTextLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("text/plain")
    ) { uri ->
        onSaveToFileActivityResult(context, viewModel, uri, ExportType.TEXT)
    }

    val saveHarLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/har+json")
    ) { uri ->
        onSaveToFileActivityResult(context, viewModel, uri, ExportType.HAR)
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            viewModel.permissionRequest.collect {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    val showClearDialog = {
        dialogConfig = DialogConfig(
            data = getClearDialogData(context),
            onPositiveClick = { viewModel.clearTransactions() }
        )
    }

    val showExportTextDialog = {
        dialogConfig = DialogConfig(
            data = getExportDialogData(context, R.string.chucker_export_text_http_confirmation),
            onPositiveClick = {
                exportTransactions(context, viewModel, "transactions.txt") { txns ->
                    TransactionListDetailsSharable(txns, encodeUrls = false)
                }
            }
        )
    }

    val showExportHarDialog = {
        dialogConfig = DialogConfig(
            data = getExportDialogData(context, R.string.chucker_export_har_http_confirmation),
            onPositiveClick = {
                exportTransactions(context, viewModel, "transactions.har") { txns ->
                    TransactionDetailsHarSharable(
                        HarUtils.harStringFromTransactions(
                            transactions = txns,
                            name = context.getString(R.string.chucker_name),
                            version = context.getString(R.string.chucker_version)
                        )
                    )
                }
            }
        )
    }

    val showSaveDialog = { type: ExportType ->
        when (type) {
            ExportType.TEXT -> saveTextLauncher.launch("transactions.txt")
            ExportType.HAR -> saveHarLauncher.launch("transactions.har")
        }
    }

    ChuckerMainScreen(
        transactions = transactions,
        applicationName = applicationName,
        onTransactionClick = onTransactionClick,
        onClearClick = showClearDialog,
        onShareTextClick = showExportTextDialog,
        onShareHarClick = showExportHarDialog,
        onSaveTextClick = { showSaveDialog(ExportType.TEXT) },
        onSaveHarClick = { showSaveDialog(ExportType.HAR) },
        onQueryChange = { viewModel.updateItemsFilter(it) }
    )

    ChuckerAlertDialog(
        config = dialogConfig,
        onDismiss = { dialogConfig = null }
    )
}
