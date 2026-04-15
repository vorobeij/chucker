package com.chuckerteam.chucker.internal.ui.compose.screens.payload

import android.content.Context
import android.text.Html.fromHtml
import android.text.SpannableStringBuilder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.support.calculateLuminance
import com.chuckerteam.chucker.internal.ui.compose.screens.payload.states.TransactionPayloadScreenEmpty
import com.chuckerteam.chucker.internal.ui.compose.screens.payload.states.TransactionPayloadScreenLoading
import com.chuckerteam.chucker.internal.ui.compose.screens.payload.states.TransactionPayloadScreenSuccess
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TransactionPayloadScreen(
    payloadType: PayloadType,
    transaction: HttpTransaction?,
    formatRequestBody: Boolean,
    onSaveToFile: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    var items by remember { mutableStateOf(listOf<TransactionPayloadItem>()) }
    var showEmptyState by remember { mutableStateOf(false) }
    var showSearchSummary by remember { mutableStateOf(false) }
    var searchSummaryText by remember { mutableStateOf("") }

    LaunchedEffect(transaction, payloadType, formatRequestBody) {
        isLoading = true
        delay(100) // Simulate async work matching original coroutine dispatch
        val result = processPayload(payloadType, transaction, formatRequestBody, context)
        items = result
        showEmptyState = result.isEmpty()
        isLoading = false
    }

    when {
        isLoading -> TransactionPayloadScreenLoading()
        showEmptyState -> TransactionPayloadScreenEmpty(payloadType)
        else -> TransactionPayloadScreenSuccess(showSearchSummary, searchSummaryText, items)
    }
}

private  fun processPayload(
    type: PayloadType,
    transaction: HttpTransaction?,
    formatRequestBody: Boolean,
    context: Context
): List<TransactionPayloadItem> {
    if (transaction == null) return emptyList()
    val result = mutableListOf<TransactionPayloadItem>()

    val headersString = if (type == PayloadType.REQUEST) {
        transaction.getRequestHeadersString(true)
    } else {
        transaction.getResponseHeadersString(true)
    }
    if (headersString.isNotBlank()) {
        result.add(TransactionPayloadItem.HeaderItem(fromHtml(headersString, android.text.Html.FROM_HTML_MODE_LEGACY)))
    }

//    if (type == PayloadType.RESPONSE && transaction.responseImageBitmap != null) {
//        result.add(TransactionPayloadItem.ImageItem(transaction.responseImageBitmap, transaction.responseImageBitmap?.calculateLuminance()))
//        return result
//    }

    val bodyString = when {
        type == PayloadType.REQUEST && formatRequestBody -> transaction.getSpannedRequestBody(context)
        type == PayloadType.REQUEST -> transaction.requestBody ?: ""
        else -> transaction.getSpannedResponseBody(context)
    }

    when {
        (type == PayloadType.REQUEST && transaction.isRequestBodyEncoded) ||
            (type == PayloadType.RESPONSE && transaction.isResponseBodyEncoded) -> {
            result.add(TransactionPayloadItem.BodyLineItem(SpannableStringBuilder.valueOf(context.getString(R.string.chucker_body_omitted))))
        }

        bodyString.isBlank() -> {
            result.add(TransactionPayloadItem.BodyLineItem(SpannableStringBuilder.valueOf(context.getString(R.string.chucker_body_empty))))
        }

        else -> {
            bodyString.lines().forEach {
                result.add(
                    TransactionPayloadItem.BodyLineItem(
                        it as? SpannableStringBuilder ?: SpannableStringBuilder.valueOf(it)
                    )
                )
            }
        }
    }
    return result
}

