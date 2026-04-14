package com.chuckerteam.chucker.internal.ui.compose

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction

internal class TransactionPreviewProvider : PreviewParameterProvider<HttpTransaction> {
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
