package com.chuckerteam.chucker.internal.ui.compose.screens.overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme

@Composable
internal fun TransactionOverviewScreen(
    transaction: HttpTransaction,
    encodeUrl: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        OverviewRow(label = R.string.chucker_url, value = transaction.getFormattedUrl(encodeUrl))
        OverviewRow(label = R.string.chucker_method, value = transaction.method)
        OverviewRow(label = R.string.chucker_protocol, value = transaction.protocol)
        OverviewRow(label = R.string.chucker_status, value = transaction.status?.toString())
        OverviewRow(label = R.string.chucker_response, value = transaction.responseSummaryText)

        if (transaction.isSsl != null) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.chucker_base_grid)))
            OverviewRow(
                label = R.string.chucker_ssl,
                value = stringResource(if (transaction.isSsl == true) R.string.chucker_yes else R.string.chucker_no)
            )
        }

        if (transaction.responseTlsVersion != null) {
            OverviewRow(label = R.string.chucker_tls_version, value = transaction.responseTlsVersion)
        }
        if (transaction.responseCipherSuite != null) {
            OverviewRow(label = R.string.chucker_tls_cipher_suite, value = transaction.responseCipherSuite)
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.chucker_base_grid)))
        OverviewRow(label = R.string.chucker_request_time, value = transaction.requestDateString)
        OverviewRow(label = R.string.chucker_response_time, value = transaction.responseDateString)
        OverviewRow(label = R.string.chucker_duration, value = transaction.durationString)
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.chucker_base_grid)))
        OverviewRow(label = R.string.chucker_request_size, value = transaction.requestSizeString)
        OverviewRow(label = R.string.chucker_response_size, value = transaction.responseSizeString)
        OverviewRow(label = R.string.chucker_total_size, value = transaction.totalSizeString)
    }
}

@Composable
private fun OverviewRow(label: Int, value: String?, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = stringResource(id = label),
            style = AppTheme.typography.labelLarge,
            modifier = Modifier.weight(0.28f)
        )
        Text(
            text = value ?: "",
            style = AppTheme.typography.bodyMedium,
            maxLines = 4,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(0.72f)
        )
    }
}

@AppPreview
@Composable
private fun TransactionOverviewScreenPreview(
    @PreviewParameter(TransactionPreviewProvider::class) transaction: HttpTransaction
) {
    AppTheme {
        TransactionOverviewScreen(transaction, encodeUrl = false)
    }
}

private class TransactionPreviewProvider : PreviewParameterProvider<HttpTransaction> {
    override val values: Sequence<HttpTransaction> = sequenceOf(
        HttpTransaction().apply {
            id = 1L
            requestDate = System.currentTimeMillis()
            responseDate = (requestDate ?:0) + 142L
            method = "GET"
            host = "api.example.com"
            protocol = "HTTP/1.1"
            responseCode = 200
            responseMessage = "OK"
            responseTlsVersion = "TLSv1.3"
            responseCipherSuite = "TLS_AES_256_GCM_SHA384"
            requestPayloadSize = 0
            responsePayloadSize = 4096
        }
    )
}
