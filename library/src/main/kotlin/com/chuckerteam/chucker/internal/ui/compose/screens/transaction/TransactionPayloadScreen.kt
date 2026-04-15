package com.chuckerteam.chucker.internal.ui.compose.screens.transaction

import android.content.Context
import android.text.SpannableStringBuilder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme
import com.chuckerteam.chucker.internal.ui.compose.views.BodyLineItem
import com.chuckerteam.chucker.internal.ui.compose.views.HeaderItem
import com.chuckerteam.chucker.internal.ui.compose.views.ImageItem
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
    var searchScrollIndex by remember { mutableStateOf(-1) }
    var searchIndices by remember { mutableStateOf(listOf<TransactionBodyAdapter.SearchItemBodyLine>()) }
    var currentQuery by remember { mutableStateOf("") }

    // Load data
    LaunchedEffect(transaction, payloadType, formatRequestBody) {
        isLoading = true
        delay(100) // Simulate async work matching original coroutine dispatch
        val result = processPayload(payloadType, transaction, formatRequestBody, context)
        items = result
        showEmptyState = result.isEmpty()
        isLoading = false
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            isLoading -> CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(dimensionResource(id = R.dimen.chucker_doub_grid))
            )

            showEmptyState -> Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.chucker_empty_payload),
                    contentDescription = stringResource(id = R.string.chucker_body_empty),
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.chucker_color_primary)),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.chucker_octa_grid))
                        .padding(bottom = dimensionResource(id = R.dimen.chucker_quad_grid))
                )
                Text(
                    text = stringResource(
                        if (payloadType == PayloadType.RESPONSE) R.string.chucker_response_is_empty
                        else R.string.chucker_request_is_empty
                    ),
                    style = AppTheme.typography.titleMedium
                )
            }

            else -> LazyColumn {
                if (showSearchSummary) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(colorResource(id = R.color.chucker_color_primary))
                                .padding(horizontal = dimensionResource(id = R.dimen.chucker_doub_grid), vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = searchSummaryText,
                                color = colorResource(id = R.color.chucker_color_on_primary),
                                style = AppTheme.typography.bodyMedium,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { /* scroll up logic */ }) {
                                Image(
                                    painter = painterResource(id = R.drawable.chucker_ic_arrow_down),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(colorResource(id = R.color.chucker_color_on_primary)),
                                    modifier = Modifier.rotate(180f)
                                )
                            }
                            IconButton(onClick = { /* scroll down logic */ }) {
                                Image(
                                    painter = painterResource(id = R.drawable.chucker_ic_arrow_down),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(colorResource(id = R.color.chucker_color_on_primary))
                                )
                            }
                        }
                    }
                }
                itemsIndexed(items, key = { it, item -> item.hashCode() + it }) { _, item ->
                    when (item) {
                        is TransactionPayloadItem.HeaderItem -> HeaderItem(item.headers)
                        is TransactionPayloadItem.BodyLineItem -> BodyLineItem(item.line)
                        is TransactionPayloadItem.ImageItem -> ImageItem(item.image, item.luminance)
                    }
                }
            }
        }
    }
}

private suspend fun processPayload(
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
        // todo
//        result.add(TransactionPayloadItem.HeaderItem(android.text.Html.fromHtml(headersString, android.text.Html.FROM_HTML_MODE_LEGACY)))
    }

    if (type == PayloadType.RESPONSE && transaction.responseImageBitmap != null) {
        // todo
//        result.add(TransactionPayloadItem.ImageItem(transaction.responseImageBitmap, transaction.responseImageBitmap?.calculateLuminance()))
        return result
    }

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

@AppPreview
@Composable
private fun TransactionPayloadScreenPreview(
    @PreviewParameter(TransactionPreviewProvider::class) transaction: HttpTransaction
) {
    AppTheme {
        TransactionPayloadScreen(
            payloadType = PayloadType.REQUEST,
            transaction = transaction,
            formatRequestBody = true,
            onSaveToFile = {}
        )
    }
}
