package com.chuckerteam.chucker.internal.ui.compose

import android.text.format.DateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.core.content.ContextCompat
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.data.entity.HttpTransactionTuple
import com.chuckerteam.chucker.internal.ui.compose.theme.AppTheme
import com.chuckerteam.chucker.internal.ui.transaction.ProtocolResources

@Composable
internal fun TransactionListItem(
    transaction: HttpTransactionTuple,
    onClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val statusCodeColor = remember(transaction.status, transaction.responseCode) {
        when {
            transaction.status === HttpTransaction.Status.Failed -> ContextCompat.getColor(context, R.color.chucker_status_error)
            transaction.status === HttpTransaction.Status.Requested -> ContextCompat.getColor(context, R.color.chucker_status_requested)
            transaction.responseCode == null -> ContextCompat.getColor(context, R.color.chucker_status_default)
            transaction.responseCode!! >= 500 -> ContextCompat.getColor(context, R.color.chucker_status_500)
            transaction.responseCode!! >= 400 -> ContextCompat.getColor(context, R.color.chucker_status_400)
            transaction.responseCode!! >= 300 -> ContextCompat.getColor(context, R.color.chucker_status_300)
            else -> ContextCompat.getColor(context, R.color.chucker_status_default)
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(transaction.id) },
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.chucker_base_grid)),
            verticalAlignment = Alignment.Top
        ) {
            // Status code
            Text(
                text = transaction.responseCode?.toString() ?: if (transaction.status === HttpTransaction.Status.Failed) "!!!" else "",
                style = AppTheme.typography.bodyLarge,
                color = Color(statusCodeColor),
                modifier = Modifier
                    .width(dimensionResource(id = R.dimen.chucker_item_size))
                    .padding(end = dimensionResource(id = R.dimen.chucker_doub_grid))
            )

            Column(modifier = Modifier.weight(1f)) {
                // Method + Path
                Text(
                    text = "${transaction.method} ${transaction.path}",
                    style = AppTheme.typography.bodyLarge,
                    color = Color(statusCodeColor),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )

                // GraphQL info
                if (transaction.graphQlDetected) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.chucker_ic_graphql),
                            contentDescription = stringResource(id = R.string.chucker_ssl),
                            modifier = Modifier.size(dimensionResource(id = R.dimen.chucker_doub_grid))
                        )
                        Text(
                            text = transaction.graphQlOperationName ?: stringResource(id = R.string.chucker_graphql_operation_is_empty),
                            style = AppTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.chucker_half_grid))
                        )
                    }
                }

                // Host + SSL
                Row(verticalAlignment = Alignment.CenterVertically) {
                    val protocolRes = if (transaction.isSsl) ProtocolResources.Https() else ProtocolResources.Http()
                    Image(
                        painter = painterResource(id = protocolRes.icon),
                        contentDescription = stringResource(id = R.string.chucker_ssl),
                        colorFilter = ColorFilter.tint(colorResource(id = protocolRes.color)),
                        modifier = Modifier.size(dimensionResource(id = R.dimen.chucker_doub_grid))
                    )
                    Text(
                        text = transaction.host.orEmpty(),
                        style = AppTheme.typography.bodyMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.chucker_half_grid))
                    )
                }

                // Footer: time, duration, size
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = DateFormat.getTimeFormat(context).format(transaction.requestDate),
                        style = AppTheme.typography.bodySmall
                    )
                    if (transaction.status === HttpTransaction.Status.Complete) {
                        Text(text = transaction.tookMs?.let { "$it ms" }.orEmpty(), style = AppTheme.typography.bodySmall)
                        Text(text = transaction.totalSizeString.orEmpty(), style = AppTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionListItemPreview(
    @PreviewParameter(TransactionTuplePreviewProvider::class) transaction: HttpTransactionTuple
) {
    AppTheme {
        TransactionListItem(transaction, onClick = {})
    }
}

internal class TransactionTuplePreviewProvider : PreviewParameterProvider<HttpTransactionTuple> {
    override val values: Sequence<HttpTransactionTuple> = sequenceOf(
        HttpTransactionTuple(
            id = 1L,
            requestDate = System.currentTimeMillis(),
            method = "GET",
            path = "/api/v1/users",
            host = "api.example.com",
            graphQlOperationName = null,
            graphQlDetected = false,
            responseCode = 200,
            requestPayloadSize = 0,
            responsePayloadSize = 4096,
            tookMs = 142,
            protocol = null,
            scheme = null,
            error = null,
        ),
        HttpTransactionTuple(
            id = 2L,
            requestDate = System.currentTimeMillis(),
            method = "POST",
            path = "/graphql",
            host = "api.example.com",
            graphQlOperationName = "getUserBatch",
            graphQlDetected = true,
            responseCode = 400,
            requestPayloadSize = 1024,
            responsePayloadSize = 256,
            tookMs = 850,
            protocol = null,
            scheme = null,
            error = null,
        )
    )
}
