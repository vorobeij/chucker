package com.chuckerteam.chucker.internal.ui.compose.screens.payload.states

import android.text.SpannableStringBuilder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.chuckerteam.chucker.R
import com.chuckerteam.chucker.internal.ui.compose.screens.payload.TransactionPayloadItem
import com.chuckerteam.chucker.internal.ui.compose.views.BodyLineItem
import com.chuckerteam.chucker.internal.ui.compose.views.HeaderItem
import com.chuckerteam.design.system.theme.AppPreview
import com.chuckerteam.design.system.theme.AppTheme
import com.sebastianneubauer.jsontree.JsonTree
import com.sebastianneubauer.jsontree.TreeColors
import org.json.JSONArray
import org.json.JSONObject

@Composable
internal fun TransactionPayloadScreenSuccess(
    showSearchSummary: Boolean,
    searchSummaryText: String,
    items: List<TransactionPayloadItem>, // todo persistent list
    json: String
) {

    // todo add copy response button

    // todo move to viewmodel bg thread
    val isJson = remember(json) { json.isJson() }
    /**
     * todo
     * MyJsonTree
     * Box
     *  LazyColumn
     *
     * 1) How to flatmap it to use inside another lazycolumn?
     * 2) Can you unfold items on level 2?
     */
    if (isJson) {
        MyJsonTree(json)
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
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
            item(key = "spacer") { Spacer(modifier = Modifier.height(8.dp)) }

            // todo separate header and body items
            itemsIndexed(items, key = { it, item -> item.hashCode() + it }) { _, item ->
                when (item) {
                    is TransactionPayloadItem.HeaderItem -> HeaderItem(item.headers)
                    is TransactionPayloadItem.BodyLineItem -> BodyLineItem(item.line)
                }
            }
        }
    }
}

private fun String.isJson(): Boolean = try {
    val json = this
    if (json.startsWith("{")) {
        JSONObject(json)
    } else {
        JSONArray(json)
    }
    true
} catch (e: Exception) {
    false
}

@Composable
private fun MyJsonTree(
    json: String
) {
    JsonTree(
        json = json,
        onLoading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        icon = Icons.AutoMirrored.Filled.KeyboardArrowRight,
        showIndices = true,
        colors = TreeColors(
            keyColor = Color(0xFF1F9E8F),
            stringValueColor = Color(0xFFE9613F),
            numberValueColor = Color(0xFFF7964A),
            booleanValueColor = Color(0xFFE9BB4D),
            nullValueColor = Color(0xFFE9BB4D),
            indexColor = Color(0x991D4555),
            symbolColor = Color(0xFF1D4555),
            iconColor = Color(0xFF1D4555),
        ),
        textStyle = AppTheme.typography.bodyLarge,
        onError = { it.printStackTrace() },
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        expandSingleChildren = true
    )
}

@AppPreview
@Composable
private fun Preview(
    @PreviewParameter(StateSuccessPreviewProvider::class) state: PreviewParams
) {
    AppTheme {
        TransactionPayloadScreenSuccess(
            showSearchSummary = state.showSearchSummary,
            searchSummaryText = state.searchSummaryText,
            items = state.items,
            json = state.json
        )
    }
}

private data class PreviewParams(
    val showSearchSummary: Boolean,
    val searchSummaryText: String,
    val items: List<TransactionPayloadItem>,
    val json: String
)

private class StateSuccessPreviewProvider : PreviewParameterProvider<PreviewParams> {
    override val values: Sequence<PreviewParams> = sequenceOf(
        PreviewParams(
            showSearchSummary = true,
            searchSummaryText = "search summary",
            items = listOf(
                TransactionPayloadItem.BodyLineItem(
                    SpannableStringBuilder.valueOf("(body is empty)")
                )
            ),
            json = """
                {
                  "api_context": {
                    "request_id": "req_7f8a9b2c3d4e5f6g",
                    "timestamp_iso": "2024-10-15T08:42:19.321Z",
                    "environment": "production",
                    "version": 2.4
                  },
                  "product_data": {
                    "sku": "ENT-SRV-X900",
                    "title": "AeroCore Enterprise Rack Server",
                    "description": "The AeroCore X900 is engineered for hyperscale data centers requiring maximum compute density without compromising thermal efficiency. Built on a custom 5nm system-on-chip architecture, it integrates 256 heterogeneous processing cores, hardware-accelerated cryptography modules, and a unified memory fabric supporting up to 2TB of ECC-registered DDR6. The chassis features tool-less drive bays, redundant 3200W platinum-certified power supplies, and an AI-driven thermal management subsystem that dynamically adjusts fan curves based on real-time workload telemetry. Designed for 24/7 mission-critical operations, it delivers consistent performance under sustained 95%+ utilization while maintaining a PUE-optimized footprint. Comprehensive remote management is provided via a dedicated out-of-band controller supporting IPMI 3.0, Redfish, and secure boot attestation.",
                    "base_price_usd": 14999.50,
                    "currency_exchange_rate": 1.0823e2,
                    "in_stock": true,
                    "is_discontinued": false,
                    "warranty_months": null,
                    "specifications": {
                      "processor_cores": 256,
                      "max_ram_tb": 2,
                      "network_ports": [
                        1,
                        10,
                        25,
                        100
                      ],
                      "power_draw_watts": 1450.75,
                      "supported_os": [
                        "Linux",
                        "Windows Server",
                        "VMware ESXi",
                        null
                      ]
                    },
                    "media": {
                      "thumbnail_base64": "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8BQDwAEhQGAhKmMIQAAAABJRU5ErkJggg==...[truncated for display, represents a multi-kilobyte string]...AAAABJRU5ErkJggg==",
                      "manual_pdf_url": "https://cdn.example.com/docs/aerocore-x900-admin-v3.pdf"
                    }
                  },
                  "customer_feedback": [
                    {
                      "reviewer": "Sarah Chen, CTO @ DataForge Inc.",
                      "rating": 4.8,
                      "verified": true,
                      "comment": "We deployed six X900 units across two edge sites and observed a 41% reduction in inference latency for our computer vision pipelines. The thermal management is genuinely impressive; even during summer months with ambient temps exceeding 35°C, the system never throttled. Setup was straightforward, though the initial firmware update took longer than expected due to strict cryptographic signature verification. Documentation could be improved around the out-of-band network configuration, but once dialed in, the server operates flawlessly. Highly recommended for AI/ML workloads that require predictable performance under variable loads. Only minor drawback is the premium pricing, which is justified by the hardware quality but may not fit smaller teams' budgets.",
                      "vendor_response": null
                    }
                  ],
                  "system_diagnostics": {
                    "log_entries": [
                      "2024-10-15T08:42:19Z [INFO]  Health check initiated for cluster node-07",
                      "2024-10-15T08:42:19Z [DEBUG] Loading NVMe driver v4.1.2 with async I/O enabled",
                      "2024-10-15T08:42:20Z [WARN]  SSD wear-leveling threshold approaching 85% on bay 03",
                      "2024-10-15T08:42:20Z [ERROR] Failed to handshake with backup NTP server: timeout after 5000ms. Retrying with primary pool.",
                      "2024-10-15T08:42:21Z [INFO]  Telemetry stream stabilized. Packet loss: 0.002%, Jitter: 1.2ms"
                    ],
                    "metrics": {
                      "uptime_hours": 8765.4,
                      "error_count_today": 3,
                      "is_maintenance_window": false,
                      "last_backup_timestamp": null,
                      "cpu_load_percent": 72.3
                    }
                  },
                  "flags": {
                    "enable_telemetry": true,
                    "strict_ssl_verification": true,
                    "beta_features_enabled": false
                  }
                }
            """.trimIndent()
        )
    )
}
