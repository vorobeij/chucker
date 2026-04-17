package com.chuckerteam.chucker.internal.ui.compose.screens.main

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.data.model.DialogData
import com.chuckerteam.chucker.internal.support.FileSaver
import com.chuckerteam.chucker.internal.support.HarUtils
import com.chuckerteam.chucker.internal.support.Sharable
import com.chuckerteam.chucker.internal.support.TransactionListDetailsSharable
import com.chuckerteam.chucker.internal.support.shareAsFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.Source
import okio.buffer
import okio.source

internal fun getClearDialogData(context: Context) = DialogData(
    title = context.getString(R.string.chucker_clear),
    message = context.getString(R.string.chucker_clear_http_confirmation),
    positiveButtonText = context.getString(R.string.chucker_clear),
    negativeButtonText = context.getString(R.string.chucker_cancel)
)

internal fun getExportDialogData(context: Context, @StringRes messageRes: Int) = DialogData(
    title = context.getString(R.string.chucker_export),
    message = context.getString(messageRes),
    positiveButtonText = context.getString(R.string.chucker_export),
    negativeButtonText = context.getString(R.string.chucker_cancel)
)

internal fun getSaveDialogData(context: Context, @StringRes messageRes: Int) = DialogData(
    title = context.getString(R.string.chucker_save),
    message = context.getString(messageRes),
    positiveButtonText = context.getString(R.string.chucker_save),
    negativeButtonText = context.getString(R.string.chucker_cancel)
)

internal fun exportTransactions(
    context: Context,
    viewModel: MainViewModel,
    fileName: String,
    createSharable: suspend (List<HttpTransaction>) -> Sharable
) {
    val activity = context as? AppCompatActivity ?: return

    activity.lifecycleScope.launch {
        val txns = viewModel.getAllTransactions()
        if (txns.isEmpty()) {
            Toast.makeText(context, R.string.chucker_export_empty_text, Toast.LENGTH_SHORT).show()
            return@launch
        }

        val intent = withContext(Dispatchers.IO) {
            createSharable(txns).shareAsFile(
                activity = activity,
                fileName = fileName,
                intentTitle = context.getString(R.string.chucker_share_all_transactions_title),
                intentSubject = context.getString(R.string.chucker_share_all_transactions_subject),
                clipDataLabel = "transactions"
            )
        }

        if (intent != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(context, R.string.chucker_export_no_file, Toast.LENGTH_SHORT).show()
        }
    }
}

internal fun onSaveToFileActivityResult(
    context: Context,
    viewModel: MainViewModel,
    uri: Uri?,
    type: ExportType
) {
    if (uri == null) {
        Toast.makeText(context, R.string.chucker_save_failed_to_open_document, Toast.LENGTH_SHORT).show()
        return
    }

    val activity = context as? AppCompatActivity ?: return

    activity.lifecycleScope.launch {
        val source = runCatching { prepareDataToSave(context, viewModel, type) }.getOrNull() ?: return@launch
        val result = FileSaver.saveFile(source, uri, context.contentResolver)

        Toast.makeText(
            context,
            if (result) R.string.chucker_file_saved else R.string.chucker_file_not_saved,
            Toast.LENGTH_SHORT
        ).show()
    }
}

internal suspend fun prepareDataToSave(
    context: Context,
    viewModel: MainViewModel,
    type: ExportType
): Source? {
    val txns = viewModel.getAllTransactions()
    if (txns.isEmpty()) {
        Toast.makeText(context, R.string.chucker_save_empty_text, Toast.LENGTH_SHORT).show()
        return null
    }

    return withContext(Dispatchers.IO) {
        when (type) {
            ExportType.TEXT -> TransactionListDetailsSharable(txns, encodeUrls = false)
                .toSharableContent(context)

            ExportType.HAR -> HarUtils.harStringFromTransactions(
                txns,
                context.getString(R.string.chucker_name),
                context.getString(R.string.chucker_version)
            ).byteInputStream().source().buffer()
        }
    }
}

internal enum class ExportType(val mimeType: String) {
    TEXT("text/plain"),
    HAR("application/har+json")
}
